package com.engineer.nio;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNio { 


	public static void main(String args[]) throws Exception{ 

	//String infile = "D:\\workspace\\test\\usagetracking.log"; 
	//FileInputStream fin= new FileInputStream(infile); 
	//FileChannel fcin = fin.getChannel(); 

	int bufSize = 100; 
	File fin = new File("usagetracking.log"); 
	File fout = new File("usagetracking2.log"); 

	FileChannel fcin = new RandomAccessFile(fin, "r").getChannel(); 
	int size = (int) fcin.size();
	ByteBuffer rBuffer = ByteBuffer.allocate(size); 

	FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel(); 
	ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize); 

//	Charset charset = Charset.forName("utf-8");
//	CharsetEncoder encoder = charset.newEncoder();
//	CharsetDecoder decoder = charset.newDecoder();
//	
//	CharBuffer cBuffer = decoder.decode(rBuffer);
//	ByteBuffer bBuffer = encoder.encode(cBuffer);
	
//	fcin.read(bBuffer);
//	bBuffer.flip();

	fcin.read(rBuffer);
	rBuffer.flip();
	InputStreamReader r = new InputStreamReader(new ByteArrayInputStream(rBuffer.array()));
	
	//String s = new String(bBuffer.array());
	
	BufferedReader reader = new BufferedReader(r);
	String line = null;
	while((line = reader.readLine()) != null) {
		System.out.println(line);
		//char[] charArray = line.toCharArray();
	}
	
	//readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer); 

	System.out.print("OK!!!"); 
	} 

	public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer){ 
		String enterStr = "\n"; 
		try{ 
		byte[] bs = new byte[bufSize]; 

		int size = 0; 
		StringBuffer strBuf = new StringBuffer(""); 
		//while((size = fcin.read(buffer)) != -1){ 
		while(fcin.read(rBuffer) != -1){ 
		      int rSize = rBuffer.position(); 
		      rBuffer.rewind(); 
		      rBuffer.get(bs); 
		      rBuffer.clear(); 
		      String tempString = new String(bs, 0, rSize); 
		      //System.out.print(tempString); 
		      //System.out.print("<200>"); 

		      int fromIndex = 0; 
		      int endIndex = 0; 
		      while((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1){ 
		       String line = tempString.substring(fromIndex, endIndex); 
		       line = new String(strBuf.toString() + line); 
		       //System.out.print(line); 
		       //System.out.print("</over/>"); 
		       //write to anthone file 
		       writeFileByLine(fcout, wBuffer, line); 

		       
		       strBuf.delete(0, strBuf.length()); 
		       fromIndex = endIndex + 1; 
		      } 
		      if(rSize > tempString.length()){ 
		      strBuf.append(tempString.substring(fromIndex, tempString.length())); 
		      }else{ 
		      strBuf.append(tempString.substring(fromIndex, rSize)); 
		      } 
		} 
		} catch (IOException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
	} 

	public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line){ 
		try { 
			//write on file head 
			//fcout.write(wBuffer.wrap(line.getBytes())); 
			//wirte append file on foot 
			fcout.write(wBuffer.wrap(line.getBytes()), fcout.size()); 

		} catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
	} 

} 