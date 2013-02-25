package eu.europa.ec.eci.oct.offline.startup;

import eu.europa.ec.eci.oct.offline.support.localization.LocalizationChangedListener;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationProvider;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import eu.europa.ec.eci.oct.offline.support.swing.localization.LocalizedJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

/**
 * @author: micleva
 * @created: 11/7/11
 * @project OCT
 */
public class CryptoOfflineTool implements LocalizationChangedListener {

    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(CryptoOfflineTool.class.getName());
    /**
     * As this is the main frame of the offline tool, allow only one instance
     */
    private static CryptoOfflineTool cryptoOfflineToolInstance;
    private static Image defaultIcon;

    public JFrame frame;

    private CryptoOfflineTool() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //unable to load the nimbus look and feel..
            log.debug("Unable to load the Nimbus look and feel", e);
        }

        //initialize the frame
        setUpFrame();

        //add the menu
        frame.setJMenuBar(new CryptoOfflineMenuBar(this));

        //add the frame content
        frame.setContentPane(new CryptoOfflineContentPanel(this));

        LocalizationProvider.getInstance().addLocalizedChangedListener(this);
    }

    private void setUpFrame() {
        frame = new LocalizedJFrame("offlineTool.title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                log.info("CryptoOfflineTool closed successfully");
            }
        });
    }

    @Override
    public void onLocalizationChanged(Locale locale) {
        frame.pack();
    }

    public void show() {
        frame.pack();
        setFrameLocationOnScreen();
        frame.setResizable(false);
        frame.setVisible(true);

        StartupPassword startupPassword = new StartupPassword(frame);
        startupPassword.openAndValidatePassword();
    }

    private void setFrameLocationOnScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        int width = frame.getSize().width;
        int height = frame.getSize().height;

        //put in vertically at 33% from top and horizontally at the middle
        frame.setLocation((screenWidth / 3) - width / 2, (screenHeight / 3 - height / 2));

        if (defaultIcon != null) {
            frame.setIconImage(defaultIcon);
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public static synchronized CryptoOfflineTool initialize() {
        if (cryptoOfflineToolInstance == null) {
            cryptoOfflineToolInstance = new CryptoOfflineTool();
        }

        try {
            defaultIcon = (new ImageIcon(CryptoOfflineTool.class.getClassLoader().getResource("images/Apps-preferences-desktop-cryptography-icon.png"))).getImage();
        } catch (Exception e) {
            //being unable to load the icon should not stop loading the tool.
            log.debug("Unable to load the tool's icon", e);
        }
        log.info("CryptoOfflineTool started successfully");

        return cryptoOfflineToolInstance;
    }

    public static CryptoOfflineTool getInstance() {
        return cryptoOfflineToolInstance;
    }
}
