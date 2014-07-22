package com.engineer.main;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringTestMain {

	public static String fillBlank(String originalString, int length)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		if (originalString != null)
			sb = new StringBuffer(originalString);
		System.out.println("===" + sb.length());
		int length2 = originalString.getBytes("GBK").length;
		System.out.println(">>>" + length2);
		int spareLength = length - length2;
		for (int i = 0; i < spareLength; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.err.println(StringTestMain.fillBlank("魔力", 10));

		System.out.println(encode("350481198410230519".getBytes("utf-8")));

		System.out.println(encode("TS50000001".getBytes("utf-8")));

		System.out.println(encode("420625196901030043".getBytes("utf-8")));

		Random random = new Random();
		byte[] strBytes = new byte[10];
		random.nextBytes(strBytes);
		String caseNo = new String(strBytes, "UTF-8");
		System.out.printf("random %s \n", caseNo);
		System.out.printf("1====random %s", getCharRandom(20));

	}

	public static String getCharRandom(int length) {
		int[] array = new int[length];
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			while (true) {
				array[i] = (int) (Math.random() * 1000);
//				if ((array[i] > 64 && array[i] < 91) || (array[i] > 96 && array[i] < 123))
//					break;
//				if (array[i] > 32 && array[i] < 127)
//					break;
				if ((array[i] > 64 && array[i] < 91) || (array[i] > 96 && array[i] < 123) || (array[i] > 47 && array[i] < 58))
					break;
			}
			chars[i] = (char) array[i];
		}
		return new String(chars);
	}

	public static String encode(byte[] bstr) {
		return new BASE64Encoder().encode(bstr);
	}

	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bt;
	}

}
