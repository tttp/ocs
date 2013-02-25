package eu.europa.ec.eci.oct.offline.dialog.pwd;

import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationProvider;

import javax.swing.*;
import java.awt.*;

/**
 * @author: micleva
 * @created: 11/22/11
 * @project OCT
 */
public class SimplePasswordValidator {

    private Container parent;

    public SimplePasswordValidator(Container parent) {
        this.parent = parent;
    }

    public boolean validate(char[] pass, int minLength) {
        String pwd = new String(pass).trim();
        if (pwd.length() < minLength) {

            LocalizationProvider localizationProvider = LocalizationProvider.getInstance();
            LocalizationMessageProvider messageProvider = localizationProvider.getCurrentMessageProvider();

            JOptionPane.showMessageDialog(parent,
                    messageProvider.getMessage("dialog.password.validate.minimum.length", String.valueOf(minLength)),
                    messageProvider.getMessage("common.validation.dialog.title"),
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }
        return true;
    }

    public boolean validate(char[] pass) {
        return validate(pass, 6);
    }
}
