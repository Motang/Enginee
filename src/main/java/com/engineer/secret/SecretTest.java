package com.engineer.secret;

import java.security.Key;

import junit.framework.Assert;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;


public class SecretTest {

	//16进制字符串编码/解码
	@Test
	public void testHex() {
		String str = "hello";
		String base64Encoded = Hex.encodeToString(str.getBytes());
		System.out.println("hex:" + base64Encoded);
		String str2 = new String(Hex.decode(base64Encoded.getBytes()));
		Assert.assertEquals(str, str2);
	}
	
	//base64编码/解码
	@Test
	public void testBase64() {
		String str = "hello";
		String base64Encoded = Base64.encodeToString(str.getBytes());
		System.out.println("base64:" + base64Encoded);
		String str2 = Base64.decodeToString(base64Encoded);
		Assert.assertEquals(str, str2);
	}
	
	@Test
	public void testMd5() {
		String str = "hello";
		String salt = "123";
		
		//MD5散列
		String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()&nbsp;
		System.out.println("md5:" + md5);
		
		//使用SHA256算法生成相应的散列数据，另外还有如SHA1、SHA512算法。   
		String sha1 = new Sha256Hash(str, salt).toString(); 
		System.out.println("Sha256Hash:" + sha1);
		
		//通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现。
		String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
		System.out.println("simpleHash:" + simpleHash);
	}
	
	@Test
	public void testSecret() {
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
		hashService.setHashIterations(1); //生成Hash值的迭代次数

		HashRequest request = new HashRequest.Builder()
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
		            .setSalt(ByteSource.Util.bytes("123")).setIterations(1).build();
		String hex = hashService.computeHash(request).toHex();
		System.out.println("testSecret:" + hex);
	}
	
	//AES
	@Test
	public void test() {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); //设置key长度
		//生成key
		Key key = aesCipherService.generateNewKey();
		String text = "hello";
		//加密
		String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		//解密
		String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

		Assert.assertEquals(text, text2);
	}
}
