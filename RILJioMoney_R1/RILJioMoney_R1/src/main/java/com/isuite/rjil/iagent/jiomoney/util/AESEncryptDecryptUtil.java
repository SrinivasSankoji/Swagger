package com.isuite.rjil.iagent.jiomoney.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class AESEncryptDecryptUtil 
{

	private static Logger logger = Logger.getLogger(AESEncryptDecryptUtil.class);
	private static final String ALGO = "AES";
	private static final byte[] m_secretKeyValue = new byte[] { 'F', '4', '2', 'E', 'C', 'B', '1', '7', 'A', 'D', '8', 'A', '5','F', ' ', '1' };

	/**
	 * method used to decrypt text provided as input using AES algorithm
	 * 
	 * @param encryptedText
	 * @return String
	 * @throws Exception
	 * 
	 */
	public static String decryptData(String encryptedText) throws Exception {
		String plainText = "";
		try 
		{
			byte[] bytePlainText = Base64.decodeBase64(encryptedText);
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.DECRYPT_MODE, getEncryptionKey());
			plainText = new String(cipher.doFinal(bytePlainText));
		} catch (Exception e) 
		{
			logger.error("Exception in [AESEncryptDecryptUtil]: [decryptData] " + e);
			throw new Exception(e.getMessage(), e);
		}
		return plainText;
	}

	/**
	 * method used to encrypt text provided as input using AES algorithm
	 * 
	 * @param plainText
	 * @return String
	 * @throws Exception
	 * 
	 */

	public static String encryptData(String plainText) throws Exception {
		String encryptedText = "";
		try {
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, getEncryptionKey());
			byte[] byteEncryptedText = cipher.doFinal(plainText.getBytes());
			encryptedText = new Base64().encodeAsString(byteEncryptedText);
		} catch (Exception e) {
			logger.error("Exception in [AESEncryptDecryptUtil]: [encryptData] " + e);
			throw new Exception(e.getMessage(), e);
		}
		return encryptedText;
	}

	/**
	 * method used to generate encryption key using a secret key
	 * 
	 * @return Key
	 * @throws Exception
	 */
	private static Key getEncryptionKey() throws Exception {
		Key encryptionkey = null;
		try {
			encryptionkey = new SecretKeySpec(m_secretKeyValue, ALGO);
		} catch (Exception e) {
			logger.error("Error while getting encryption key [AESEncryptDecryptUtil]: [getEncryptionKey] " + e);
			throw new Exception(e.getMessage(), e);
		}
		return encryptionkey;
	}
}
