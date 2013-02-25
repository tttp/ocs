package eu.europa.ec.eci.oct.offline.dialog.pwd;

import eu.europa.ec.eci.oct.offline.startup.CryptoOfflineTool;
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
 * @created: 11/22/11
 * @project OCT
 */
public abstract class PasswordDialog extends JDialog {

    private static final long serialVersionUID = 1816284675007177106L;

    private Container parent;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private SimplePasswordValidator passwordValidator;

    private boolean showExitButton = false;

    public PasswordDialog(Container parent, boolean showExitButton) {
        this.parent = parent;
        this.showExitButton = showExitButton;

        passwordValidator = new SimplePasswordValidator(this);

        //do not allow closing this dialog
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //the dialog needs to be modal in order to not allow interactions with the
        // behind components while this one is used
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.setResizable(false);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        //add all the components to this panel
        addWidgets(container);
    }

    public PasswordDialog(Container parent) {
        this(parent, false);
    }

    private void addWidgets(Container container) {

        JPanel detailsPanel = getExplanatoryPanel();
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        container.add(detailsPanel);

        JPanel passPanel = new JPanel();
        passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.X_AXIS));
        passPanel.add(new LocalizedJLabel("dialog.password.label"));
        passPanel.add(Utils.getXSeparator(10));

        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');
        passPanel.add(passwordField);

        passPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        container.add(passPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        confirmButton = new LocalizedJButton("dialog.password.confirm.button");
        buttonPanel.add(confirmButton);
        if (showExitButton) {
            JButton closeButton = new LocalizedJButton("common.exit");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame jFrame = CryptoOfflineTool.getInstance().getFrame();
                    jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
                }
            });
            buttonPanel.add(Utils.getXSeparator(5));
            buttonPanel.add(closeButton);
        }

        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        container.add(buttonPanel);
    }

    public void openDialog() {
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    public void setPasswordConfirmedAction(ActionListener actionListener) {
        confirmButton.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
    }

    public boolean validatePassword() {
        return passwordValidator.validate(passwordField.getPassword());
    }

    public abstract JPanel getExplanatoryPanel();

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
