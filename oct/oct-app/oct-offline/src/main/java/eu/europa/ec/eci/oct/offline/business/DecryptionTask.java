package eu.europa.ec.eci.oct.offline.business;

import eu.europa.ec.eci.export.model.*;
import eu.europa.ec.eci.oct.crypto.CipherOperation;
import eu.europa.ec.eci.oct.crypto.CryptoException;
import eu.europa.ec.eci.oct.crypto.Cryptography;
import eu.europa.ec.eci.oct.offline.business.reader.FormattedFileReader;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriter;
import eu.europa.ec.eci.oct.offline.support.crypto.CryptographyHelper;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import static eu.europa.ec.eci.oct.offline.business.reader.FormattedFileReaderProvider.getFormattedFileReaderProvider;
import static eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriterProvider.getFormattedFileWriter;

/**
 * @author: micleva
 * @created: 11/18/11
 * @project OCT
 */
class DecryptionTask implements Callable<DecryptTaskStatus> {
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(DecryptionTask.class.getName());

    private final File fileInSelection;
    private final File selectedInput;
    private final File outputFolder;
    private final FileType outputFileType;

    private boolean isInterrupted = false;
    private int decryptedData = 0;

    /**
     * Cryptography is not thread safe!
     * As such, use one instance per thread
     */
    private Cryptography cryptography = null;

    public DecryptionTask(File fileInSelection, File selectedInput, File outputFolder, FileType outputFileType) {
        this.fileInSelection = fileInSelection;
        this.selectedInput = selectedInput;
        this.outputFolder = outputFolder;
        this.outputFileType = outputFileType;
    }

    @Override
    public DecryptTaskStatus call() throws Exception {

        boolean success = true;
        long startTime = System.currentTimeMillis();
        String additionalMessage = null;
        try {

            FormattedFileReader fileReader = getFormattedFileReaderProvider(FileType.XML);
            SupportForm supportForm = fileReader.readFromFile(fileInSelection);

            decryptSupportForm(supportForm);

            if (!isInterrupted) {
                FormattedFileWriter fileWriter = getFormattedFileWriter(outputFileType);
                fileWriter.writeToOutputRelativeToInputPath(supportForm, outputFolder, fileInSelection, selectedInput);
            } else {
                additionalMessage = "Decryption interrupted!";
                success = false;
            }

        } catch (Throwable e) {
            log.debug("Unable to decrypt file: " + fileInSelection.getAbsolutePath(), e);
            success = false;
            additionalMessage = e.getMessage();
        }
        long execTime = System.currentTimeMillis() - startTime;
        if (success) {
            additionalMessage = "Decrypted data: " + decryptedData;
        }

        return new DecryptTaskStatus(fileInSelection.getAbsolutePath(), execTime, success, additionalMessage);
    }

    private void initCryptography() throws CryptoException {
        if (cryptography == null) {
            cryptography = new Cryptography(CipherOperation.DECRYPT, CryptographyHelper.getKey());
        }
    }

    private void decryptSupportForm(SupportForm supportForm) throws CryptoException, DecoderException {

        initCryptography();

        List<SignatureType> signatureTypes = supportForm.getSignatures().getSignature();

        for (Iterator<SignatureType> iterator = signatureTypes.iterator(); iterator.hasNext() && !verifyInterrupted();) {
            SignatureType signatureType = iterator.next();
            SignatoryType signatoryType = signatureType.getSignatoryInfo();

            List<GroupType> groupList = signatoryType.getGroups().getGroup();
            for (Iterator<GroupType> iterator1 = groupList.iterator(); iterator1.hasNext() && !verifyInterrupted();) {
                GroupType groupType = iterator1.next();
                List<PropertyType> propertyTypeList = groupType.getProperties().getProperty();
                for (Iterator<PropertyType> iterator2 = propertyTypeList.iterator(); iterator2.hasNext() && !verifyInterrupted();) {
                    PropertyType propertyType = iterator2.next();
                    propertyType.setValue(decrypt("group.property.key -> " + propertyType.getKey(), propertyType.getValue()));
                }
            }
        }
    }

    private boolean verifyInterrupted() {
        isInterrupted |= Thread.interrupted();

        return isInterrupted;
    }

    private String decrypt(String fieldName, String filedValue) throws DecoderException, CryptoException {
        if (isInterrupted) {
            return "";
        }
        try {
            return new String(cryptography.perform(Hex.decodeHex(filedValue.trim().toCharArray())), DecryptConstants.CHARACTER_ENCODING);
        } catch (DecoderException e) {
            throw new DecoderException("Unable to decode field name: " + fieldName + " -> " + e.getMessage(), e);
        } catch (CryptoException e) {
            throw new CryptoException("Unable to decrypt field name: " + fieldName + " -> " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException("Unable to decode field name: " + fieldName + " -> " + e.getMessage(), e);
        } finally {
            decryptedData++;
        }
    }
}
