package eu.europa.ec.eci.oct.offline.startup;

import eu.europa.ec.eci.oct.offline.actions.DefaultActionHandler;
import eu.europa.ec.eci.oct.offline.actions.OfflineToolCommandAction;
import eu.europa.ec.eci.oct.offline.support.Utils;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJButton;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * @author: micleva
 * @created: 11/7/11
 * @project OCT
 */
public class CryptoOfflineContentPanel extends JPanel {

    private static final long serialVersionUID = -3740762807793917860L;

    CryptoOfflineTool cryptoOfflineTool;

    public CryptoOfflineContentPanel(CryptoOfflineTool cryptoOfflineTool) {

        this.cryptoOfflineTool = cryptoOfflineTool;

        //setup the panel
        setupPanel();

        //add widgets
        addWidgets();
    }

    private void setupPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
    }

    private void addWidgets() {

        JPanel firstRow = new JPanel();
        firstRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));

        //add the label to the first line
        firstRow.add(new LocalizedJLabel("decrypt.login.challenge.label"));

        //add a small separator
        firstRow.add(Utils.getXSeparator(15));

        //Add the two buttons for choosing de desired action
        firstRow.add(Box.createHorizontalGlue());
        JButton decryptLoginChallenge = new LocalizedJButton("decrypt.login.challenge.button.label", "decrypt.login.challenge.tooltip");
        decryptLoginChallenge.addActionListener(new DefaultActionHandler(cryptoOfflineTool));
        decryptLoginChallenge.setActionCommand(OfflineToolCommandAction.DECRYPT_LOGIN_CHALLENGE.name());
        firstRow.add(decryptLoginChallenge);

        JPanel secondRow = new JPanel();
        secondRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
        secondRow.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        secondRow.add(new LocalizedJLabel("decrypt.exportedData.label"));

        secondRow.add(Box.createHorizontalGlue());
        JButton decryptExportedData = new LocalizedJButton("decrypt.exportedData.button.label", "decrypt.exportedData.tooltip");
        decryptExportedData.addActionListener(new DefaultActionHandler(cryptoOfflineTool));
        decryptExportedData.setActionCommand(OfflineToolCommandAction.DECRYPT_EXPORTED_DATA.name());
        secondRow.add(decryptExportedData);

        this.add(firstRow);
        this.add(secondRow);

        JPanel closeButtonPanel = new JPanel();
        closeButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        closeButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        closeButtonPanel.setLayout(new BoxLayout(closeButtonPanel, BoxLayout.X_AXIS));
        JButton close = new LocalizedJButton("common.close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = CryptoOfflineTool.getInstance().getFrame();
                jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        close.setAlignmentX(Component.LEFT_ALIGNMENT);
        closeButtonPanel.add(close);
        this.add(closeButtonPanel);
    }
}
