package eu.europa.ec.eci.oct.offline.dialog.initialize;

import eu.europa.ec.eci.oct.crypto.Cryptography;
import eu.europa.ec.eci.oct.offline.dialog.pwd.PasswordDialog;
import eu.europa.ec.eci.oct.offline.support.crypto.CryptographyHelper;
import eu.europa.ec.eci.oct.offline.support.crypto.KeyProvider;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * @author: micleva
 * @created: 11/9/11
 * @project OCT
 */
class InitializeActionHandler implements ActionListener {

    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(InitializeActionHandler.class.getName());

    private JTextArea responseTextArea;
    private JTextArea publicKeyTextArea;
    private JTextField hashedPasswordResult;
    private JDialog jDialog;

    InitializeActionHandler(JTextArea responseTextArea, JTextArea publicKeyTextArea, JTextField hashedPasswordResult, JDialog jDialog) {
        this.responseTextArea = responseTextArea;
        this.publicKeyTextArea = publicKeyTextArea;
        this.hashedPasswordResult = hashedPasswordResult;
        this.jDialog = jDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // If the tool is already initialized, display warnings.. ask for a password.. etc
        final PasswordDialog passwordDialog = new PasswordInitializationDialog(jDialog);
        passwordDialog.setPasswordConfirmedAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (passwordDialog.validatePassword()) {
                    passwordDialog.dispose();
                    proceedWithGeneratingKeys(passwordDialog.getPassword());
                }
            }
        });

        passwordDialog.openDialog();
    }

    private void proceedWithGeneratingKeys(final String passwordForKey) {
        final Component glassPane = jDialog.getGlassPane();

        SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
            @Override
            protected String doInBackground() throws Exception {
                String publicKeyHexEncoded = null;
                try {
                    KeyPair keyPair = Cryptography.generateKeyPair();

                    //for now just print out the private key
                    PrivateKey privateKey = keyPair.getPrivate();
                    KeyProvider.saveCryptoKeyWithPassword(privateKey, passwordForKey);

                    publicKeyHexEncoded = new String(Hex.encodeHex(keyPair.getPublic().getEncoded()));

                    KeyProvider.saveOctKeyWithPassword(keyPair.getPublic());
                    CryptographyHelper.initiateCryptoModule(passwordForKey);


                    publicKeyTextArea.setText(publicKeyHexEncoded);
                    responseTextArea.setText("Successfully initialized. Please copy the Public Key in the " +
                            "Online Collection System.");
                    hashedPasswordResult.setText(new String(Hex.encodeHex(Cryptography.fingerprint(passwordForKey.getBytes("UTF-8")))));
                } catch (Exception ex) {
                    responseTextArea.setText("Unable to generate the key pairs: " + ex.getMessage());
                    log.debug("Unable to generate the key pairs", ex);
                } finally {
                    //whatever happens, release the lock on the dialog
                    jDialog.setEnabled(true);
                    glassPane.setVisible(false);
                }

                return publicKeyHexEncoded;
            }

            @Override
            protected void done() {
                //re-enable the button

            }
        };

        glassPane.setVisible(true);
        jDialog.setEnabled(false);
        worker.execute();
    }
}
