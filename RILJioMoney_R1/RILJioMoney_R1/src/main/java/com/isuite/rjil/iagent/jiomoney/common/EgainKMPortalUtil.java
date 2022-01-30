package com.isuite.rjil.iagent.jiomoney.common;


import java.security.NoSuchAlgorithmException

;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.isuite.rjil.iagent.jiomoney.exception.DataAccessException;


public class EgainKMPortalUtil 
{
	private static final Logger logger = Logger.getLogger(EgainKMPortalUtil.class);
    private static final String ALGO = "AES";
	private static final String KEYGEN_SPEC = "PBKDF2WithHmacSHA1";
	private static final int SALT_LENGTH = 16; // in bytes
	private static final int AUTH_KEY_LENGTH = 8; // in bytes
	private static final int ITERATIONS = 32768;
	private static final String PRIVATE_KEY="eGain123";
	private  String salt1;
	private String name = "";
	private String content="";
	
	public String encrypt(String Data) throws Exception 
	{
		Keys key = keygen(128,PRIVATE_KEY.toCharArray(),generateSalt(SALT_LENGTH));
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key.encryption);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = Base64.encodeBase64URLSafeString(encVal);
		return encryptedValue;
    }

    public String decrypt(String encryptedData) throws Exception 
    {
    	Keys key = keygen(128,PRIVATE_KEY.toCharArray(),Base64.decodeBase64(salt1));
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key.encryption);
		byte[] decordedValue = Base64.decodeBase64(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
    }

    private byte[] generateSalt(int length) 
    {
		Random r = new SecureRandom();
		byte[] salt = new byte[length];
		r.nextBytes(salt);
		salt1 = Base64.encodeBase64URLSafeString(salt);
		System.out.println("salt---> "+salt1);
		return salt;
	}
    
    private static Keys keygen(int keyLength, char[] password, byte[] salt) 
    {
		SecretKeyFactory factory;
		try 
		{
			factory = SecretKeyFactory.getInstance(KEYGEN_SPEC);
		} 
		catch (NoSuchAlgorithmException impossible) 
		{ 
			return null; 
		}
		// derive a longer key, then split into AES key and authentication key
		KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, keyLength + AUTH_KEY_LENGTH * 8);
		SecretKey tmp = null;
		try 
		{
			tmp = factory.generateSecret(spec);
		} 
		catch (InvalidKeySpecException lException) { }
		byte[] fullKey = tmp.getEncoded();
		SecretKey authKey = new SecretKeySpec( // key for password authentication
		Arrays.copyOfRange(fullKey, 0, AUTH_KEY_LENGTH), "AES");
		SecretKey encKey = new SecretKeySpec( // key for AES encryption
		Arrays.copyOfRange(fullKey, AUTH_KEY_LENGTH, fullKey.length), "AES");
		return new Keys(encKey, authKey);
	}
    private static class Keys 
    {
		public final SecretKey encryption, authentication;
		public Keys(SecretKey encryption, SecretKey authentication) 
		{
			this.encryption = encryption;
			this.authentication = authentication;
		}
	}

	public void encryptEgainKMDetails(String agentid,String pRequestId) throws DataAccessException 
	{
		try 
		{
			String encryptedAgentId = encrypt(agentid);
			setName(encryptedAgentId);
			setContent(salt1);
			logger.info("Egain KM portal- encrypted agent id-"+encryptedAgentId+" salt-"+salt1);

		} catch (Exception e) 
		{
			logger.error("Problem into egain KM portal-"+e.getMessage());
			throw new DataAccessException("Problem while encryting content : "+e.getMessage() );
		}	
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
}

