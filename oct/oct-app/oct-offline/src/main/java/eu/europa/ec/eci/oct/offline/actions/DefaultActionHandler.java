package eu.europa.ec.eci.oct.offline.actions;

import eu.europa.ec.eci.oct.offline.dialog.NotImplementedDialog;
import eu.europa.ec.eci.oct.offline.dialog.challenge.ChallengeDecryptionDialog;
import eu.europa.ec.eci.oct.offline.dialog.export.DecryptExportDialog;
import eu.europa.ec.eci.oct.offline.dialog.hashpass.HashPasswordDialog;
import eu.europa.ec.eci.oct.offline.dialog.help.HelpDialog;
import eu.europa.ec.eci.oct.offline.dialog.initialize.CryptoInitializeDialog;
import eu.europa.ec.eci.oct.offline.startup.CryptoOfflineTool;
import eu.europa.ec.eci.oct.offline.support.crypto.CryptographyHelper;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: micleva
 * @created: 11/7/11
 * @project OCT
 */
public class DefaultActionHandler implements ActionListener {

    private JFrame cryptoToolFrame;

    public DefaultActionHandler(CryptoOfflineTool cryptoOfflineTool) {
        cryptoToolFrame = cryptoOfflineTool.getFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        OfflineToolCommandAction offlineToolAction = OfflineToolCommandAction.valueOf(e.getActionCommand());

        switch (offlineToolAction) {
            case INITIALIZE_CRYPTO_OFFLINE_TOOL:
                CryptoInitializeDialog initializeDialog = new CryptoInitializeDialog(cryptoToolFrame);
                initializeDialog.show();
                break;
            case DECRYPT_LOGIN_CHALLENGE:
                ChallengeDecryptionDialog decryptionDialog = new ChallengeDecryptionDialog(cryptoToolFrame);
                if (validateCryptoModule()) {
                    decryptionDialog.show();
                }
                break;
            case DECRYPT_EXPORTED_DATA:
                DecryptExportDialog exportDialog = new DecryptExportDialog(cryptoToolFrame);
                if (validateCryptoModule()) {
                    exportDialog.show();
                }
                break;
            case SHOW_HELP:
                HelpDialog helpDialog = new HelpDialog(cryptoToolFrame);
                helpDialog.show();
                break;
            case HASH_PASSWORD:
                HashPasswordDialog hashPasswordDialog = new HashPasswordDialog(cryptoToolFrame);
                hashPasswordDialog.show();
                break;
            default:
                NotImplementedDialog notImplementedDialog = new NotImplementedDialog(offlineToolAction);
                notImplementedDialog.show();
        }
    }

    private boolean validateCryptoModule() {
        LocalizationMessageProvider messageProvider = LocalizationProvider.getInstance().getCurrentMessageProvider();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageProvider.getMessage("decrypt.export.validate.toFix"));

        boolean isValid = true;
        if (!CryptographyHelper.isCryptoModuleInitialized()) {
            stringBuilder.append('\n').append(messageProvider.getMessage("decrypt.key.notInitialized"));
            isValid = false;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(cryptoToolFrame,
                    stringBuilder.toString(),
                    messageProvider.getMessage("common.validation.dialog.title"),
                    JOptionPane.WARNING_MESSAGE);
        }

        return isValid;
    }
}
