package com.engineer.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class FileNioTest {
	public static void main(String[] args) throws Exception {
		//dddd();
		long begin = System.currentTimeMillis();
		copy("usagetracking.log","output.txt");
		//copy("focalWorkflow.log.2014-01-26","output.txt");
		System.out.println(System.currentTimeMillis()-begin);
	}

	private static void dddd() throws FileNotFoundException, IOException, CharacterCodingException {
		File input = new File("usagetracking.log");
		File output = new File("output.txt");
		
		FileInputStream in = new FileInputStream(input);
		FileOutputStream out = new FileOutputStream(output);
		
		FileReader reader = new FileReader(input);
		BufferedReader bi = new BufferedReader(reader);
		String readLine = bi.readLine();
		
		FileChannel inChannel = in.getChannel();
		FileChannel outChannel = out.getChannel();
		
		Charset charset = Charset.forName("utf-8");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while(true) {
			buffer.clear();
			CharBuffer decode = decoder.decode(buffer);
			ByteBuffer encode = encoder.encode(decode);
			
			int read = inChannel.read(encode);
			if (read == -1) {
				break;
			}
			encode.flip();
			
//			InputStreamReader r = new InputStreamReader(new ByteArrayInputStream(encode.array()));
//			BufferedReader breader = new BufferedReader(r);
//			String line = null;
//			while((line = breader.readLine()) != null) {
//				System.out.println(line);
//			}
			
			outChannel.write(encode);
		}
	}
	
	public static void copy(String from, String to) throws IOException {
		RandomAccessFile fromFile = new RandomAccessFile(from, "r");
		RandomAccessFile toFile = new RandomAccessFile(to, "rw");
		
		FileChannel fromChannel = fromFile.getChannel();
		FileChannel toChannel = toFile.getChannel();
		System.out.println(toChannel.size());
		toChannel.position(toChannel.size());
		long position = 0;
		long count = fromChannel.size();
		
		fromChannel.transferTo(position, count, toChannel);
		//toChannel.transferFrom(fromChannel, position, count);
		
		fromChannel.close();
		toChannel.close();
		

	}
}
