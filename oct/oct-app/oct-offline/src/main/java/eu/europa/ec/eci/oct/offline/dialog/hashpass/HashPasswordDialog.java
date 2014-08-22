package eu.europa.ec.eci.oct.offline.dialog.hashpass;

import eu.europa.ec.eci.oct.crypto.CryptoException;
import eu.europa.ec.eci.oct.crypto.Cryptography;
import eu.europa.ec.eci.oct.offline.dialog.menu.CopyPasteContextualMenu;
import eu.europa.ec.eci.oct.offline.dialog.pwd.SimplePasswordValidator;
import eu.europa.ec.eci.oct.offline.startup.SecurityConstants;
import eu.europa.ec.eci.oct.offline.support.Utils;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJButton;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJDialog;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: micleva
 * @created: 12/21/11
 * @project OCT
 */
public class HashPasswordDialog {
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(HashPasswordDialog.class.getName());

    private JDialog jDialog;
    private JFrame ownerFrame;
    private SimplePasswordValidator passwordValidator;

    public HashPasswordDialog(JFrame owner) {
        this.ownerFrame = owner;
        jDialog = new LocalizedJDialog(owner, "menu.hashPassword.label");
        jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jDialog.setResizable(false);

        //by default destroy all the objects associated with this dialog upon close
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //add widgets
        addWidgetsToContainer();

        passwordValidator = new SimplePasswordValidator(jDialog.getParent());
    }

    private void addWidgetsToContainer() {

        JPanel hashPasswordPanel = new JPanel();
        hashPasswordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hashPasswordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        hashPasswordPanel.setLayout(new BoxLayout(hashPasswordPanel, BoxLayout.Y_AXIS));

        hashPasswordPanel.add(new LocalizedJLabel("dialog.password.label"));
        hashPasswordPanel.add(Utils.getYSeparator(3));

        final JPasswordField passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        hashPasswordPanel.add(passwordField);

        hashPasswordPanel.add(Utils.getYSeparator(10));

        //the hashed password
        hashPasswordPanel.add(new LocalizedJLabel("crypto.init.password.hash.result"));
        hashPasswordPanel.add(Utils.getYSeparator(3));

        //the challenge response text
        final JTextField hashedPasswordResult = new JTextField(20);
        hashedPasswordResult.setEditable(false);
        hashedPasswordResult.setAlignmentX(Component.LEFT_ALIGNMENT);
        hashedPasswordResult.addMouseListener(new CopyPasteContextualMenu());
        hashPasswordPanel.add(hashedPasswordResult);
        hashPasswordPanel.add(Utils.getYSeparator(10));

        JPanel hashButtonPanel = new JPanel();
        hashButtonPanel.setLayout(new BoxLayout(hashButtonPanel, BoxLayout.X_AXIS));
        hashButtonPanel.add(Box.createHorizontalGlue());
        hashButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton hashButton = new LocalizedJButton("menu.hashPassword.label");
        hashButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        hashButtonPanel.add(hashButton);
        hashPasswordPanel.add(hashButtonPanel);

        JButton button = new LocalizedJButton("common.close");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        hashPasswordPanel.add(button);

        jDialog.add(hashPasswordPanel);

        //link things together
        hashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passChars = passwordField.getPassword();
                if (passwordValidator.validate(passChars, SecurityConstants.MINIMUM_PASSWORD_LENGTH)) {
                    String pass = new String(passChars);
                    try {
                        hashedPasswordResult.setText(new String(Hex.encodeHex(Cryptography.fingerprint(pass.getBytes()))));
                    } catch (CryptoException ex) {
                        hashedPasswordResult.setText("Unable to hash the password: " + ex.getMessage());
                        log.debug("Unable to hash the password: " + ex.getMessage(), ex);
                    }
                }
            }
        });
    }

    public void show() {
        jDialog.pack();
        jDialog.setLocationRelativeTo(ownerFrame);
        jDialog.setVisible(true);
    }
}
