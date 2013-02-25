package eu.europa.ec.eci.oct.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Wrapper class encapsulating leveraging java.security package 
 * 
 * @author marcin.dzierzak@ext.ec.europa.eu
 *
 */
public class Cryptography {

	private int opMode;
	
	private Cipher cipher;

	/**
	 * Creates an <code>Cryptography</code> for given operation type and key value
	 * 
	 * @param co - operation type
	 * @param keyValue - serialized key value
	 * @throws CryptoException - if algorithms not found or key is invalid
	 */
	public Cryptography(CipherOperation co, byte[] keyValue) throws CryptoException{
		
		opMode = getOperationMode(co);
		Key key;
		try {
			key = initKey(keyValue);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("no such key algorithm : " + CryptoConstants.KEY_ALG, e);
		} catch (InvalidKeySpecException e) {
			throw new CryptoException("invalid key specification", e);
		}
		
		try {
			cipher = Cipher.getInstance(CryptoConstants.CIPHER_ALG);
			cipher.init(opMode, key);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("no such cipher algorithm : " + CryptoConstants.CIPHER_ALG, e);
		} catch (NoSuchPaddingException e) {
			throw new CryptoException("no such padding", e);
		} catch (InvalidKeyException e) {
			throw new CryptoException("invalid key", e);
		}
		
	}
	
	/**
	 * Generates <code>KeyPair</code> instance. Uses RSA algorithm and key size is 2048
	 * @return generated key pair
	 * @throws CryptoException - if algorithm not found
	 */
	public static KeyPair generateKeyPair() throws CryptoException {
		
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(CryptoConstants.KEY_ALG);
			generator.initialize(2048);
			return generator.generateKeyPair();

		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("no such key algorithm : " + CryptoConstants.KEY_ALG, e);
		}
	}
	
	/**
	 * Counts hash of the given byte array. Digester algorithm is SHA1
	 * @param bytes - input byte array 
	 * @return - hash of the input byte array
	 * @throws CryptoException - if digester algorithm not found
	 */
	public static byte[] fingerprint(byte[] bytes) throws CryptoException {
		
		try {
			return MessageDigest.getInstance(CryptoConstants.DIGESTER_ALG).digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException("no such digest algorithm : " + CryptoConstants.DIGESTER_ALG, e);
		}
	}
	
	
	/**
	 * Performs cryptographic operation
	 * 
	 * @param bytes - byte array to encode
	 * @return - cryptographic operation result
	 * @throws CryptoException - if block size or padding incorrect
	 */
	public byte[] perform(byte[] bytes) throws CryptoException{
		
		try {
			return cipher.doFinal(bytes);			
		} catch (IllegalBlockSizeException e) {
			throw new CryptoException("Illegal block size", e);
		} catch (BadPaddingException e) {
			throw new CryptoException("Bad padding", e);
		}
	}
	
	
	private int getOperationMode(CipherOperation co){
		
		if(CipherOperation.DECRYPT.equals(co)){
			return Cipher.DECRYPT_MODE;
		} else if(CipherOperation.ENCRYPT.equals(co)){
			return Cipher.ENCRYPT_MODE;
		} else {
			throw new IllegalArgumentException("Illegal cipher operation: " + co.name());
		}
	}
	
	private Key initKey(byte[] keyValue) throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		KeyFactory kf = KeyFactory.getInstance(CryptoConstants.KEY_ALG);
		Key localKey;
		
		switch (opMode) {
		case Cipher.DECRYPT_MODE:
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyValue);
			localKey = kf.generatePrivate(keySpec);
			break;
		case Cipher.ENCRYPT_MODE:
			X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(keyValue);
			localKey = kf.generatePublic(x509Spec);
			break;
		default:
			throw new IllegalArgumentException("Illegal operation mode: " + opMode);
		
		}
		return localKey;
	}
}
