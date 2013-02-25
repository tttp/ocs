package eu.europa.ec.eci.oct.offline.support;

import eu.europa.ec.eci.oct.offline.support.crypto.KeyProvider;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author: micleva
 * @created: 11/8/11
 * @project OCT
 */
public class Utils {
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(Utils.class.getName());

    private static final long ONE_HOUR = 60 * 60 * 1000;
    private static final long ONE_MINUTE = 60 * 1000;
    private static final long ONE_SECOND = 1000;

    public static Component getXSeparator(int width) {
        return Box.createHorizontalStrut(width);
    }

    public static Component getYSeparator(int height) {
        return Box.createVerticalStrut(height);
    }

    /**
     * Switches the enable state of all the components given as an input parameter and
     * pf all the child components of the components given as input
     *
     * @param components   the components that should change the state
     * @param enabledState true to put the components on enabled states, false to disable them
     */
    public static void switchComponentsIncludingChildrenState(Component[] components, boolean enabledState) {
        for (Component component : components) {
            component.setEnabled(enabledState);
            if (component instanceof Container) {
                Container container = (Container) component;
                Component[] childComponents = container.getComponents();
                if (childComponents.length > 0) {
                    switchComponentsIncludingChildrenState(childComponents, enabledState);
                }
            }
        }
    }

    /**
     * Returns a formatted elapsed time since a given time in millies.
     * The format looks like this: hours:minutes:seconds
     *
     * @param startTime the time since the elapsed time is calculated
     * @return the elapsed time in format hours:minutes:seconds
     */
    public static String getFormattedElapsedTimeSince(long startTime) {
        long passedMillis = System.currentTimeMillis() - startTime;
        long hours = passedMillis / ONE_HOUR;
        long minutes = (passedMillis % ONE_HOUR) / ONE_MINUTE;
        long seconds = ((passedMillis % ONE_HOUR) % ONE_MINUTE) / ONE_SECOND;

        StringBuilder formattedTime = new StringBuilder();

        if (hours < 10) {
            formattedTime.append('0');
        }
        formattedTime.append(hours).append(':');
        if (minutes < 10) {
            formattedTime.append('0');
        }
        formattedTime.append(minutes).append(':');
        if (seconds < 10) {
            formattedTime.append('0');
        }
        formattedTime.append(seconds);

        return formattedTime.toString();
    }

    public static String getFolderPathInProject(String folderName) throws UnsupportedEncodingException {

        File currentFolder = new File(KeyProvider.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        currentFolder = currentFolder.isDirectory() ? currentFolder : currentFolder.getParentFile();
        File parent = currentFolder.getParentFile();

        StringBuilder folderPathBuilder = new StringBuilder();
        try {
            folderPathBuilder.append(parent.getCanonicalPath());
        } catch (IOException e) {
            folderPathBuilder.append(parent.getAbsolutePath());
            log.debug("Unable to read the canonical path for file: " + parent, e);
        }
        folderPathBuilder.append('/').append(folderName);

        return URLDecoder.decode(folderPathBuilder.toString(), System.getProperty("file.encoding"));
    }
}
