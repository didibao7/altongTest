package com.altong.web.logic.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESCrypto {
	private String iv;
	private Key keySpec;
	
	public static int KEYSIZE_AES_128 = 16;
	public static int KEYSIZE_AES_192 = 24;
	public static int KEYSIZE_AES_256 = 32;

	public AESCrypto(final String key, final int keySize) throws Exception {
		byte[] keyBytes = null;
		byte[] b = key.getBytes(StandardCharsets.UTF_8);
		String strCipher = null;
		
		switch (keySize)
		{
		case 16:	// 128 bit
			strCipher = "AES-128";
			keyBytes = new byte[keySize];
			break;
		case 24:	// 192 bit
			strCipher = "AES-192";
			keyBytes = new byte[keySize];
			break;
		case 32:	// 256 bit
			strCipher = "AES-256";
			keyBytes = new byte[keySize];
			break;
		}
		if (keyBytes == null) {
			throw new Exception("암호화 방식이 올바르지 않습니다.");
		}
		System.out.println("Encrypt Type : "+strCipher);
		
		int inputKeyLength = key.length();
		if (inputKeyLength > keySize) {
			// 입력 받은 key string 길이가 실제 암호화 할 대상 key 길이보다 큰 경우, 암호화 키 길이에 맞게 조정
            System.arraycopy(b, 0, keyBytes, 0, keySize);
		}
		else if (inputKeyLength < keySize) {
			throw new Exception("Key 길이가 올바르지 않습니다.");
		}
		else {
			// 입력 받은 key string 길이가, 실제 암호화 할 대상 key 길이와 동일한 경우
			System.arraycopy(b, 0, keyBytes, 0, keySize);
		}
		
		// AES 암호화는 IV 값으로 16 byte 사용
		this.iv = key.substring(0, 16);
		
		// KeySpec 생성 시 입력하는 키 길이에 따라 AES-128,192,256 방식으로 자동 설정 됨
		this.keySpec = new SecretKeySpec(keyBytes, "AES");
		
		System.out.println("KEY : "+new String(keyBytes, "UTF-8"));
		System.out.println("IV : "+this.iv);
	}

	public String aesEncode(String str) throws Exception {
		System.out.println("INPUT : "+str);
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, this.keySpec, new IvParameterSpec(this.iv.getBytes(StandardCharsets.UTF_8)));
		byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
		//new String(Base64.getEncoder().encode(encrypted))
		return Hex.encodeHexString(Base64.getEncoder().encode(encrypted));
	}

	public String aesDecode(String str) throws Exception {
		System.out.println("INPUT : "+str);
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, this.keySpec, new IvParameterSpec(this.iv.getBytes(StandardCharsets.UTF_8)));
		byte[] decrypted = c.doFinal(Base64.getDecoder().decode(str));
		return new String(decrypted, "UTF-8");
	}
	
	
	public String encryption(String str) throws Exception {
		byte[] bytes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes();
		System.out.println("encryption : " + str);
		SecureRandom sr = SecureRandom.getInstance("SHAIPRNG");
		sr.setSeed(bytes);;
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256, sr);;
		
		SecretKey sKey = kgen.generateKey();
		SecretKeySpec skeySpec = new SecretKeySpec(sKey.getEncoded(), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] encrypted = c.doFinal(str.getBytes());
		System.out.println("encrypted : " + encrypted);
		return Hex.encodeHexString(encrypted);
	}
	
	//sha256 암호화
	public static String sha256(String msg) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(msg.getBytes());
	    StringBuilder builder = new StringBuilder();
	    for (byte b: md.digest()) {
		      builder.append(String.format("%02x", b));
		    }
	    return builder.toString();
	}

	public static byte[] encrypt (byte[] plaintext,SecretKey key,byte[] IV ) throws Exception
    {
        //Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        //Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
        //Create IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);
        
        return cipherText;
    }
	
}
