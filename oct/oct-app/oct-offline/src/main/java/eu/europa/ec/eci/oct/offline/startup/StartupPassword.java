package eu.europa.ec.eci.oct.offline.startup;

import eu.europa.ec.eci.oct.offline.dialog.pwd.PasswordDialog;
import eu.europa.ec.eci.oct.offline.support.crypto.CryptographyHelper;
import eu.europa.ec.eci.oct.offline.support.crypto.KeyProvider;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationProvider;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: micleva
 * @created: 11/22/11
 * @project OCT
 */
public class StartupPassword extends PasswordDialog {

	private static final long serialVersionUID = -1808347305605480559L;

	public StartupPassword(Container parent) {
        super(parent, true);
    }

    @Override
    public JPanel getExplanatoryPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new LocalizedJLabel("crypto.startup.password.details"));

        return panel;
    }

    public void openAndValidatePassword() {
        if (KeyProvider.keyFileExists()) {

            final PasswordDialog pwdDialog = this;
            setPasswordConfirmedAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pwd = pwdDialog.getPassword();
                    if (pwd.length() > 0) {
                        if (CryptographyHelper.initiateCryptoModule(pwd)) {
                            pwdDialog.dispose();
                        } else {

                            LocalizationProvider localizationProvider = LocalizationProvider.getInstance();
                            LocalizationMessageProvider messageProvider = localizationProvider.getCurrentMessageProvider();

                            JOptionPane.showMessageDialog(pwdDialog,
                                    messageProvider.getMessage("crypto.startup.error"),
                                    messageProvider.getMessage("common.validation.dialog.title"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            super.openDialog();
        }
    }
}
