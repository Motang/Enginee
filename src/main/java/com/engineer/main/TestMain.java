package com.engineer.main;


public class TestMain {
	public static void main(String[] args) {
//		try {
//			String urlString = "eadefakiaHR0cDovL3d3dy5zaG9weHgubmV0L2NlcnRpZmljYXRlLmFjdGlvbj9zaG9wVXJsPQ";
//			BASE64Decoder bASE64Decoder = new BASE64Decoder();
//			urlString = new String(bASE64Decoder.decodeBuffer(StringUtils.substring(urlString, 8) + "=="));
//			System.err.println(urlString);
//			URL url = new URL(urlString + "http://www.shopxx.cn");
//			URLConnection urlConnection = url.openConnection();
//			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
//			int responseCode = httpConnection.getResponseCode();
//			System.out.println(">>>>>>"+responseCode);
//			
//			System.err.println(Integer.MAX_VALUE);
//			
//			char c = '聙';
//			System.out.println((int)c);
//			
//			String line = "The=Java=platform";
//			StringTokenizer st = new StringTokenizer(line, "=", false);
//			System.out.println("Token Total: " + st.countTokens());
//			int i = 0;
//			while (st.hasMoreElements() &&  i++ < 4) {
//				System.out.println(st.nextToken());
//			}
//			
//			//while (st.hasMoreElements()) {
//			//	System.out.println(st.nextToken());
//			//}
//		} catch (IOException e) {
//			
//		}
		System.out.println(1<<0|1<<1);
		System.out.println(3&1);
		System.out.println(3&2);
		System.out.println(1<<1|1<<2);
		System.out.println(6&2);
		System.out.println(6&4);
		System.out.println(1<<0|1<<2);
		System.out.println(5&1);
		System.out.println(5&4);
		System.out.println(1<<0|1<<3);
		System.out.println(9&1);
		System.out.println(9&8);
		System.out.println(1<<1|1<<3);
		System.out.println(10&2);
		System.out.println(10&8);
		System.out.println(1<<2|1<<3);
		System.out.println(12&4);
		System.out.println(12&8);
		System.out.println(1<<0|1<<4);
		System.out.println(17&1);
		System.out.println(17&16);
		System.out.println(1<<1|1<<4);
		System.out.println(18&2);
		System.out.println(18&16);
		System.out.println(1<<2|1<<4);
		System.out.println(20&4);
		System.out.println(20&16);
		System.out.println(1<<3|1<<4);
		System.out.println(24&4);
		System.out.println(24&8);
		
	}
}
