package eu.europa.ec.eci.oct.offline.support.crypto;

import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import static eu.europa.ec.eci.oct.offline.support.Utils.getDataFile;

/**
 * @author: micleva
 * @created: 11/22/11
 * @project OCT
 */
public class KeyProvider {

    private static final String OCT_KEY_FILE = "oct.key";
    private static final String CRYPTO_KEY_FILE = "crypto.key";
    private static final String CRYPTO_SALT_FILE = "crypto.salt";
    private static final int DIGEST_ITERATIONS = 2500;

    public static void saveCryptoKeyWithPassword(PrivateKey privateKey, char[] pass) throws Exception {
        byte[] priv = privateKey.getEncoded();

        SecureRandom saltGenerator = new SecureRandom();
        byte[] salt = saltGenerator.generateSeed(256);

        byte[] encBytes = PbeCipher.encrypt(priv, pass, salt, DIGEST_ITERATIONS);

        String hexEnc = new String(Hex.encodeHex(encBytes));

        //write the private key and the salt used for password in external files
        writeBytesToFile(hexEnc.getBytes("UTF-8"), CRYPTO_KEY_FILE);
        writeBytesToFile(salt, CRYPTO_SALT_FILE);
    }

    public static void saveOctKeyWithPassword(PublicKey publicKey) throws Exception {
        String hexEncoded = new String(Hex.encodeHex(publicKey.getEncoded()));

        //write the private key and the salt used for password in external files
        writeBytesToFile(hexEncoded.getBytes("UTF-8"), OCT_KEY_FILE);
    }

    public static byte[] loadKeyFromFile(char[] pass) throws Exception {

        //read the encrypted private key bytes and the salt used for password encryption from files
        byte[] keyData = readBytesFromFile(CRYPTO_KEY_FILE);
        byte[] saltData = readBytesFromFile(CRYPTO_SALT_FILE);

        byte[] encryptedKey = Hex.decodeHex(new String(keyData, "UTF-8").toCharArray());

        return PbeCipher.decrypt(encryptedKey, pass, saltData, DIGEST_ITERATIONS);
    }

    private static byte[] readBytesFromFile(String filePath) throws Exception {
        File file = getDataFile(filePath);

        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] theData = new byte[(int) file.length()];
        dis.readFully(theData);
        dis.close();
        return theData;
    }

    private static void writeBytesToFile(byte[] bytes, String filePath) throws Exception {
        File file = getDataFile(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        dos.write(bytes);
        dos.close();
    }

    public static boolean keyFileExists() {
        try {
            return getDataFile(CRYPTO_KEY_FILE).exists();
        } catch (Exception e) {
            return false;
        }
    }
}
