package eu.europa.ec.eci.oct.offline.support.crypto;

import eu.europa.ec.eci.oct.crypto.CryptoException;
import eu.europa.ec.eci.oct.crypto.Cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: micleva
 * @created: 12/8/11
 * @project OCT
 */
class PbeCipher {

    private static final String ENCRYPT_ALGORITHM = "AES";

    static byte[] encrypt(byte[] dataToEncrypt, String plainTextPass, byte[] salt, int iterations) throws CryptoException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (salt == null || salt.length < 8) {
            throw new IllegalArgumentException("salt needs to be at least 8 bytes long");
        }

        //produces a 256 bit key, 32 byte long array
        byte[] key = buildKey(plainTextPass, salt, iterations);

        key = Arrays.copyOf(key, 16); // use only first 128 bit

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ENCRYPT_ALGORITHM);

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        return cipher.doFinal(dataToEncrypt);
    }

    static byte[] decrypt(byte[] dataToDecrypt, String plainTextPass, byte[] salt, int iterations) throws CryptoException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] key = buildKey(plainTextPass, salt, iterations);

        key = Arrays.copyOf(key, 16); // use only first 128 bit

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ENCRYPT_ALGORITHM);

        // Instantiate the cipher
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        return cipher.doFinal(dataToDecrypt);
    }

    private static byte[] buildKey(String plainTextPass, byte[] salt, int iterations) throws CryptoException, UnsupportedEncodingException {
        byte[] pass = plainTextPass.getBytes("UTF-8");

        byte[] hashedData = mixDataInDeterministicOrder(salt, pass);
        for (int i = 0; i < iterations; i++) {
            hashedData = Cryptography.fingerprint(hashedData);
        }
        return hashedData;
    }

    private static byte[] mixDataInDeterministicOrder(byte[] salt, byte[] pass) {
        List<Byte> dataContent = new ArrayList<Byte>();
        for (byte data : salt) {
            dataContent.add(data);
        }
        int arrayLength = dataContent.size();
        for (byte data : pass) {
            int position = data % arrayLength;
            dataContent.add(position, data);
        }
        byte[] result = new byte[dataContent.size()];
        for (int i = 0, dataContentSize = dataContent.size(); i < dataContentSize; i++) {
            Byte data = dataContent.get(i);
            result[i] = data;
        }
        return result;
    }
}
