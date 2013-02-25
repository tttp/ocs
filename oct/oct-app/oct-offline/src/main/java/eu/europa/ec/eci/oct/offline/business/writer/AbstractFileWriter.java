package eu.europa.ec.eci.oct.offline.business.writer;

import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.FileType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author: micleva
 * @created: 4/2/12
 * @project ECI
 */
public abstract class AbstractFileWriter implements FormattedFileWriter {

    private static final String DECRYPT_FILE_SUFFIX = "_dec";

    private final FileType fileType;
    private Set<String> existingFolders = Collections.synchronizedSet(new HashSet<String>());

    protected AbstractFileWriter(FileType fileType) {
        this.fileType = fileType;
    }

    protected File buildOutputFile(File outputFolder, File fileInSelection, File selectedInput, Locale locale) throws DataException {

        String outputPath = buildOutputFilePath(outputFolder.getAbsolutePath(), fileInSelection, selectedInput, locale);
        try {

            File outputFile = new File(outputPath);

            List<String> pathsToRoot = getFoldersPathsToRoot(outputFile, outputFolder);
            //remove the root as we start the concatenation from the root already
//        pathToRoot.remove(outputFolderName);

            //create any missing folder:s up to the output folder root
            List<File> foldersToCreate = new ArrayList<File>();
            for (String folderPath : pathsToRoot) {

                if (!existingFolders.contains(folderPath)) {
                    File folder = new File(folderPath);
                    if (folder.exists()) {
                        existingFolders.add(folderPath);
                    } else {
                        foldersToCreate.add(folder);
                    }
                }
            }
            if (!foldersToCreate.isEmpty()) {
                //the folders to create are now ordered from the most inner folder to the outer folder.
                // In order to be able to create them, we need to reverse the order
                Collections.reverse(foldersToCreate);
                for (File folder : foldersToCreate) {
                    boolean wasCreated = folder.mkdir();
                    if (wasCreated) {
                        existingFolders.add(folder.getAbsolutePath());
                    }
                }
            }

            if (outputFile.exists()) {
                //if the file exists, find a new file name which does not exist
                outputFile = findNewFileNameBasedOnFile(outputFile);
            }

            //finally, create the new file
            boolean wasCreated = outputFile.createNewFile();
            if (!wasCreated) {
                // if the new file was not created (it already existed),
                // then something is wrong with the logic
                throw new IllegalStateException("Output file \"" + outputFile +
                        "\" was not created, since it already existed. This should not happen.");
            }

            return outputFile;
        } catch (IOException e) {
            throw new DataException("Unable to build the output file: " + outputPath, e);
        }
    }

    private File findNewFileNameBasedOnFile(File outputFile) {

        String fileNameNoExtension = getFileNameWithoutExtension(outputFile);
        String fileExtension = getFileExtension(outputFile);

        String absoluteFolderPath = outputFile.getAbsolutePath();
        absoluteFolderPath = absoluteFolderPath.substring(0, absoluteFolderPath.indexOf(fileNameNoExtension));

        StringBuilder prefixNameBuilder = new StringBuilder();
        prefixNameBuilder.append(absoluteFolderPath);
        prefixNameBuilder.append(File.separator);
        prefixNameBuilder.append(fileNameNoExtension);
        prefixNameBuilder.append(" (");

        String prefix = prefixNameBuilder.toString();
        String suffix = ")." + fileExtension;

        File newFile = null;
        for (int i = 2; i < Integer.MAX_VALUE && newFile == null; i++) {
            File tmpFile = new File(prefix + i + suffix);
            if (!tmpFile.exists()) {
                newFile = tmpFile;
            }
        }

        return newFile;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int extensionStartPos = fileName.lastIndexOf('.');

        String extension = "";
        if (extensionStartPos > 0 && extensionStartPos < fileName.length() - 1) {
            extension = fileName.substring(extensionStartPos + 1);
        }
        return extension;
    }

    private String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int extensionStartPos = fileName.lastIndexOf('.');

        if (extensionStartPos > 0 && extensionStartPos < fileName.length() - 1) {
            fileName = fileName.substring(0, extensionStartPos);
        }
        return fileName;
    }

    private String buildOutputFilePath(String outputFileAbsolutePath, File fileInSelection,
                                       File selectedInput, Locale locale) {
        StringBuilder outputFilePath = new StringBuilder();
        outputFilePath.append(outputFileAbsolutePath);

        if (selectedInput.isDirectory()) {
            //build the path inside the output folder

            //get the input file path according to the input root file
            String inputRootPath = selectedInput.getAbsolutePath();
            String inputFilePath = fileInSelection.getAbsolutePath();
            //remove the root from the path
            String pathFromInputRootToFile = inputFilePath.substring(inputRootPath.length());
            //remove the inputFile simple name from the path as the file name will be modified
            pathFromInputRootToFile = pathFromInputRootToFile.substring(0, (pathFromInputRootToFile.length() - fileInSelection.getName().length()));

            //append the input root filename to the output folder path
            outputFilePath.append(File.separator);
            outputFilePath.append(selectedInput.getName());

            //append the path from root to file
            outputFilePath.append(File.separator);
            outputFilePath.append(pathFromInputRootToFile);
        }
        outputFilePath.append(File.separator);
        outputFilePath.append(getFileNameWithoutExtension(fileInSelection));

        //add the suffix
        outputFilePath.append(DECRYPT_FILE_SUFFIX);
        if(locale != null && locale.getLanguage() != null) {
            outputFilePath.append('_').append(locale.getLanguage().toLowerCase());
        }
        outputFilePath.append('.');
        outputFilePath.append(fileType.name().toLowerCase());

        return outputFilePath.toString();
    }

    private List<String> getFoldersPathsToRoot(File fileUnderRoot, File rootFile) {
        List<String> pathToFile = new ArrayList<String>();

        File currentFile = fileUnderRoot;
        while (!currentFile.equals(rootFile)) {
            currentFile = currentFile.getParentFile();
            pathToFile.add(currentFile.getAbsolutePath());
        }

        return pathToFile;
    }

    @Override
    public void writeToOutputRelativeToInputPath(SupportForm supportForm, File outputFolder,
                                                 File fileInSelection, File selectedInput) throws DataException {
        List<Locale> linguisticVersions = getLinguisticVersions(supportForm.getForCountry());

        if(linguisticVersions != null && !linguisticVersions.isEmpty()) {
            for (Locale linguisticVersion : linguisticVersions) {
                writeLinguisticVersion(supportForm, outputFolder, fileInSelection, selectedInput, linguisticVersion);
            }
        } else {
            writeLinguisticVersion(supportForm, outputFolder, fileInSelection, selectedInput, null);
        }
    }

    private void writeLinguisticVersion(SupportForm supportForm, File outputFolder, File fileInSelection, File selectedInput, Locale linguisticVersion) throws DataException {
        File outputFile = buildOutputFile(outputFolder, fileInSelection, selectedInput, linguisticVersion);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(outputFile);

            fillUpContent(supportForm, fileOutputStream, linguisticVersion);

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e1) {
                    //ignore any exception here
                }
            }
            throw new DataException("Unable to write the output file: " + outputFile.getAbsolutePath(), e);
        }
    }

    protected abstract void fillUpContent(SupportForm supportForm, FileOutputStream out, Locale locale) throws Exception;

    protected abstract List<Locale> getLinguisticVersions(String countryCode);
}
