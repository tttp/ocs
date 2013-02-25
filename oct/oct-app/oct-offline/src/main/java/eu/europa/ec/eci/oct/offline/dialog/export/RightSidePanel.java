package eu.europa.ec.eci.oct.offline.dialog.export;

import eu.europa.ec.eci.oct.offline.business.FileType;
import eu.europa.ec.eci.oct.offline.support.Utils;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJButton;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author: micleva
 * @created: 11/14/11
 * @project OCT
 */
public class RightSidePanel extends JPanel {

	private static final long serialVersionUID = -1302465560571524404L;

	private Container parent;

    // fields which will be needed outside
    private File outputFolder;
    private JButton startDecryptionButton;
    private JLabel totalNumberOfFilesLabel;
    private JLabel selectedFilesSize;

    private JComboBox fileTypesSelection;

    public RightSidePanel(Container parent) {
        super();

        this.parent = parent;

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // show the summary panel
        JPanel summaryPanel = buildSelectionSummaryPanel();
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(summaryPanel);

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
//        separator.setBackground(Color.blue);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(separator);

        //build the output folders and the output file type
        JPanel outputFolderAndTypePanel = buildOutputFolderAndTypePanel();
        outputFolderAndTypePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(outputFolderAndTypePanel);

        this.add(Box.createVerticalGlue());

        //build the output folders and the output file type
        JPanel buttonsPanel = buildButtonsPanel();
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(buttonsPanel);
    }

    private JPanel buildButtonsPanel() {

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

        startDecryptionButton = new LocalizedJButton("decrypt.export.dialog.start.decryption.process");
        buttonsPanel.add(startDecryptionButton);

        return buttonsPanel;
    }

    private JPanel buildSelectionSummaryPanel() {
        JPanel selectionSummary = new JPanel();
        selectionSummary.setLayout(new BoxLayout(selectionSummary, BoxLayout.Y_AXIS));
        selectionSummary.setMaximumSize(new Dimension(500, 150));

        JLabel label = new LocalizedJLabel("decrypt.export.dialog.files.summary.title");
        label.setFont(new Font("", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionSummary.add(label);
        selectionSummary.add(Utils.getYSeparator(2));

        //build the line displaying the number of files selected
        JPanel summaryLinePanel = new JPanel();
        summaryLinePanel.setLayout(new BoxLayout(summaryLinePanel, BoxLayout.X_AXIS));
        summaryLinePanel.add(new LocalizedJLabel("decrypt.export.dialog.files.summary.selected.files.total",
                "decrypt.export.dialog.files.summary.selected.files.total.tooltip"));
        summaryLinePanel.add(Utils.getXSeparator(3));
        totalNumberOfFilesLabel = new JLabel("0");
        summaryLinePanel.add(totalNumberOfFilesLabel);
        summaryLinePanel.add(Box.createGlue());

        summaryLinePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionSummary.add(summaryLinePanel);

        //build the line displaying the file size
        summaryLinePanel = new JPanel();
        summaryLinePanel.setLayout(new BoxLayout(summaryLinePanel, BoxLayout.X_AXIS));
        summaryLinePanel.add(new LocalizedJLabel("decrypt.export.dialog.files.summary.selected.files.size.total",
                "decrypt.export.dialog.files.summary.selected.files.size.total.tooltip"));
        summaryLinePanel.add(Utils.getXSeparator(3));
        selectedFilesSize = new JLabel("0");
        summaryLinePanel.add(selectedFilesSize);

        summaryLinePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectionSummary.add(summaryLinePanel);

        return selectionSummary;
    }

    private JPanel buildOutputFolderAndTypePanel() {

        JPanel outputFolderAndTypePanel = new JPanel();
        outputFolderAndTypePanel.setLayout(new BoxLayout(outputFolderAndTypePanel, BoxLayout.Y_AXIS));

        JPanel outputFolderPanel = new JPanel();
        outputFolderPanel.setLayout(new BoxLayout(outputFolderPanel, BoxLayout.X_AXIS));
        outputFolderPanel.setMaximumSize(new Dimension(500, 150));

        JLabel label = new LocalizedJLabel("decrypt.export.dialog.output.label");
        label.setPreferredSize(new Dimension(85, 25));
        outputFolderPanel.add(label);

        //add the folder name
        final JTextField outputFolderText = new JTextField();
        outputFolderText.setEditable(false);
        outputFolderText.setPreferredSize(new Dimension(180, 25));
        outputFolderPanel.add(Utils.getXSeparator(5));
        outputFolderPanel.add(outputFolderText);

        //add the browse button
        JButton browseFolder = new LocalizedJButton("decrypt.export.dialog.output.browse");
        outputFolderPanel.add(Utils.getXSeparator(5));
        outputFolderPanel.add(browseFolder);

        browseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = chooser.showOpenDialog(parent);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    outputFolder = chooser.getSelectedFile();
                    outputFolderText.setText(outputFolder.getAbsolutePath());
                    outputFolderText.setToolTipText(outputFolder.getAbsolutePath());
                    outputFolderText.setCaretPosition(0);
                }
            }
        });

        outputFolderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        outputFolderAndTypePanel.add(outputFolderPanel);

        JPanel outputFileTypeSelectionPanel = new JPanel();
        outputFileTypeSelectionPanel.setLayout(new BoxLayout(outputFileTypeSelectionPanel, BoxLayout.X_AXIS));
        outputFileTypeSelectionPanel.setMaximumSize(new Dimension(500, 150));

        label = new LocalizedJLabel("decrypt.export.dialog.output.file.type");
        label.setPreferredSize(new Dimension(85, 25));
        outputFileTypeSelectionPanel.add(label);
        outputFileTypeSelectionPanel.add(Utils.getXSeparator(3));
        fileTypesSelection = new JComboBox(FileType.values());
        fileTypesSelection.setPreferredSize(new Dimension(100, 25));
        fileTypesSelection.setSelectedIndex(0);
        outputFileTypeSelectionPanel.add(fileTypesSelection);
        outputFileTypeSelectionPanel.add(Box.createGlue());

        outputFileTypeSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        outputFolderAndTypePanel.add(outputFileTypeSelectionPanel);

        return outputFolderAndTypePanel;
    }

    public File getOutputFolder() {
        return outputFolder;
    }

    public JButton getStartDecryptionButton() {
        return startDecryptionButton;
    }

    public JLabel getTotalNumberOfFilesLabel() {
        return totalNumberOfFilesLabel;
    }

    public JLabel getSelectedFilesSize() {
        return selectedFilesSize;
    }

    public FileType getSelectedOutputFileType() {
        return (FileType) fileTypesSelection.getSelectedItem();
    }
}
