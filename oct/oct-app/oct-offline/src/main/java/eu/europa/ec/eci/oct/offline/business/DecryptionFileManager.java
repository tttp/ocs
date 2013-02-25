package eu.europa.ec.eci.oct.offline.business;

import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.reader.FormattedFileReader;
import eu.europa.ec.eci.oct.offline.business.reader.FormattedFileReaderProvider;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriter;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriterProvider;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author: micleva
 * @created: 11/24/11
 * @project OCT
 */
class DecryptionFileManager {

    private static final String DECRYPT_FILE_SUFFIX = "_dec";

    private final File outputFolder;
    private final String fileOutputSuffix;
    private final String outputFolderAbsolutePath;
    private Set<String> existingFolders = Collections.synchronizedSet(new HashSet<String>());

    private FormattedFileWriter fileWriter;
    private FormattedFileReader fileReader;

    public DecryptionFileManager(File outputFolder, FileType outputFileType) throws DataException {
        this.outputFolder = outputFolder;
        fileOutputSuffix = DECRYPT_FILE_SUFFIX + "." + outputFileType.name().toLowerCase();

        outputFolderAbsolutePath = outputFolder.getAbsolutePath();

        //initiate the reader and the writer
        fileWriter = FormattedFileWriterProvider.getFormattedFileWriter(outputFileType);
        //we always read from XML files
        fileReader = FormattedFileReaderProvider.getFormattedFileReaderProvider(FileType.XML);
    }

    private File buildOutputFile(File inputFile, File inputRootFile) throws DataException {

        String outputPath = buildOutputFilePath(inputFile, inputRootFile);
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

    private String buildOutputFilePath(File inputFile, File inputRootFile) {
        StringBuilder outputFilePath = new StringBuilder();
        outputFilePath.append(outputFolderAbsolutePath);

        if (inputRootFile.isDirectory()) {
            //build the path inside the output folder

            //get the input file path according to the input root file
            String inputRootPath = inputRootFile.getAbsolutePath();
            String inputFilePath = inputFile.getAbsolutePath();
            //remove the root from the path
            String pathFromInputRootToFile = inputFilePath.substring(inputRootPath.length());
            //remove the inputFile simple name from the path as the file name will be modified
            pathFromInputRootToFile = pathFromInputRootToFile.substring(0, (pathFromInputRootToFile.length() - inputFile.getName().length()));

            //append the input root filename to the output folder path
            outputFilePath.append(File.separator);
            outputFilePath.append(inputRootFile.getName());

            //append the path from root to file
            outputFilePath.append(File.separator);
            outputFilePath.append(pathFromInputRootToFile);
        }
        outputFilePath.append(File.separator);
        outputFilePath.append(getFileNameWithoutExtension(inputFile));
        outputFilePath.append(fileOutputSuffix);

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

    public SupportForm readFromFile(File inputFile) throws DataException {
        return fileReader.readFromFile(inputFile);
    }

    public void writeToOutputRelativeToInputPath(SupportForm supportForm, File inputFile, File inputRootFile) throws DataException {
        File outputFile = buildOutputFile(inputFile, inputRootFile);
        fileWriter.writeToOutput(supportForm, outputFile);
    }
}
