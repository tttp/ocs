package eu.europa.ec.eci.oct.offline.dialog.initialize;

import eu.europa.ec.eci.oct.offline.dialog.pwd.PasswordDialog;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author: micleva
 * @created: 11/22/11
 * @project OCT
 */
public class PasswordInitializationDialog extends PasswordDialog {

	private static final long serialVersionUID = 7338221964195203503L;

	public PasswordInitializationDialog(Container parent) {
        super(parent);
    }

    @Override
    public JPanel getExplanatoryPanel() {

        JPanel panel = new JPanel();

        panel.add(new LocalizedJLabel("crypto.init.password.details"));

        return panel;
    }
}
