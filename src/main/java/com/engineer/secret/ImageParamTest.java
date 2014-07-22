package com.engineer.secret;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class ImageParamTest {
	public static void main(String[] args) throws Exception {
		String key       = "bbafc7b2ff9c50921d909a95e53103e9";
		
		
		Cipher cipher    = Cipher.getInstance("DESede/ECB/NoPadding");
    	DESedeKeySpec keyspec = new DESedeKeySpec(key.getBytes());
    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(keyspec);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String hexString = "a6c9bae2f793c5d6f23e656c5158add48587b24917ed27d52f4e475ca15f4fcf1bdcaeabde9d49a4d6568f0105ad091762173db93852450e7099cbdc55b70f09c61e48766d4bd361f328c3404e265f22237a68afe14a7d02bd0d61ba2925183688f9207c5fd6c45407a2fbc4d16d4cf23e62802b9bbca517";
		System.out.println(new String(cipher.doFinal(toBytes(hexString)),"UTF-8"));
		
		String algorithm = "AES";
		Cipher passwordCipher = Cipher.getInstance(algorithm);
		passwordCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(toBytes(key), algorithm));
		String password = toHex(passwordCipher.doFinal("Tjm010101".getBytes()));
		System.out.println(password);
	}
	
	private static byte[] toBytes(String hexKey) {
		if (hexKey == null) return null;

		byte[] key = new byte[hexKey.length() / 2];

		for (int i = 0, j = 0; i < key.length; i++, j+=2) {
			key[i] = (byte) Integer.parseInt(hexKey.substring(j, j+2), 16);
		}

		return key;
	}
	
	public static String toHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}
	
}
