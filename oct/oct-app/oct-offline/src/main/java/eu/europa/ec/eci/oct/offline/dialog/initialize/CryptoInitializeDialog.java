package eu.europa.ec.eci.oct.offline.dialog.initialize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import eu.europa.ec.eci.oct.offline.dialog.menu.CopyPasteContextualMenu;
import eu.europa.ec.eci.oct.offline.support.Utils;
import eu.europa.ec.eci.oct.offline.support.crypto.KeyProvider;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJButton;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJDialog;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJLabel;

/**
 * @author: micleva
 * @created: 11/9/11
 * @project OCT
 */
public class CryptoInitializeDialog {

	private JDialog jDialog;
	private JFrame frame;

	public CryptoInitializeDialog(JFrame owner) {
		jDialog = new LocalizedJDialog(owner, "crypto.init.dialog.title");
		this.frame = owner;

		// by default destroy all the objects associated with this dialog upon close
		jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// the dialog needs to be modal in order to not allow interactions with the
		// behind components while this one is used
		jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		// add widgets to the dialog
		addWidgets();
	}

	private void disableInitialiseMenuItem() {
		frame.getJMenuBar().getMenu(0).getItem(0).setEnabled(!KeyProvider.keyFileExists());
	}

	private void addWidgets() {
		jDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disableInitialiseMenuItem();
			}
		});

		// set the glass pane
		JPanel glassPane = new JPanel();
		glassPane.setPreferredSize(jDialog.getSize());
		Color backColor = new Color(192, 192, 192, 100);
		glassPane.setBackground(backColor);
		glassPane.setLayout(new GridBagLayout());

		JPanel insidePanel = new JPanel();
		insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.Y_AXIS));
		insidePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		insidePanel.setBackground(Color.white);
		JLabel progressLabel = new LocalizedJLabel("crypto.init.dialog.generate");
		progressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		insidePanel.add(progressLabel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		insidePanel.add(progressBar);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		glassPane.add(insidePanel, constraints);
		jDialog.setGlassPane(glassPane);

		JPanel initializationPanel = new JPanel();
		initializationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		initializationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		initializationPanel.setLayout(new BoxLayout(initializationPanel, BoxLayout.Y_AXIS));

		JButton initButton = new LocalizedJButton("crypto.init.dialog.button.init.label");
		initButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		initializationPanel.add(initButton);
		initializationPanel.add(Utils.getYSeparator(5));

		JLabel resultLabel = new LocalizedJLabel("crypto.init.dialog.result.label");
		resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		initializationPanel.add(resultLabel);
		initializationPanel.add(Utils.getYSeparator(3));

		JTextArea responseTextArea = new JTextArea(5, 25);
		responseTextArea.setEditable(false);
		responseTextArea.setBackground(Color.white);
		responseTextArea.setLineWrap(true);
		responseTextArea.setWrapStyleWord(true);
		JScrollPane responseScrollPane = new JScrollPane(responseTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		responseScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		initializationPanel.add(responseScrollPane);
		initializationPanel.add(Utils.getYSeparator(10));

		JLabel publicKeyLabel = new LocalizedJLabel("crypto.init.dialog.publicKey.label");
		publicKeyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		initializationPanel.add(publicKeyLabel);
		initializationPanel.add(Utils.getYSeparator(3));

		JTextArea publicKeyTextArea = new JTextArea(10, 25);
		publicKeyTextArea.setBackground(Color.white);
		publicKeyTextArea.setEditable(false);
		publicKeyTextArea.addMouseListener(new CopyPasteContextualMenu());
		publicKeyTextArea.setLineWrap(true);
		final JScrollPane scrollPane = new JScrollPane(publicKeyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		initializationPanel.add(scrollPane);
		initializationPanel.add(Utils.getYSeparator(10));

		// the challenge response label
		initializationPanel.add(new LocalizedJLabel("crypto.init.password.hash.result"));
		initializationPanel.add(Utils.getYSeparator(3));

		// the challenge response text
		JTextField hashedPasswordResult = new JTextField(30);
		hashedPasswordResult.setEditable(false);
		hashedPasswordResult.setAlignmentX(Component.LEFT_ALIGNMENT);
		hashedPasswordResult.addMouseListener(new CopyPasteContextualMenu());
		initializationPanel.add(hashedPasswordResult);
		initializationPanel.add(Utils.getYSeparator(10));

		JButton button = new LocalizedJButton("common.close");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disableInitialiseMenuItem();
				jDialog.dispose();
			}
		});
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		initializationPanel.add(button);

		jDialog.add(initializationPanel);

		// add the actions for the init and re-init buttons
		initButton.addActionListener(new InitializeActionHandler(responseTextArea, publicKeyTextArea, hashedPasswordResult, jDialog));
	}

	public void show() {
		jDialog.pack();
		jDialog.setLocationRelativeTo(frame);
		jDialog.setVisible(true);
	}
}
