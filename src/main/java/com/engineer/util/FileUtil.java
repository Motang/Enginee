package com.engineer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FileUtil {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		System.out.println(getSentimentalLine("c:\\AS400_extract.sql"));
	}
	public static void writeFile(String content, String filename) throws IOException{
		
		File file_dis = new File(filename);
		if(!file_dis.isFile()){
			file_dis.createNewFile();
		}
		
		FileWriter fw = new FileWriter(filename);
		fw.write(content);
		fw.close();
	}	

	public static void writeFile(String content, String filename,String charset) throws IOException{
		File file_dis = new File(filename);
		if(!file_dis.isFile()||!file_dis.exists()){
			file_dis.createNewFile();
		}
		if(charset==null||charset.trim().length()==0){
			charset = "UTF-8";
		}
		OutputStreamWriter fw = new OutputStreamWriter(
			new FileOutputStream(filename),"UTF-8");
	
		fw.write(content);
	
		fw.flush();
		fw.close();
	}
	
	public static String getline(File file, long rownum) throws IOException{
		if(file==null||rownum<0){
			return null;
		}
		BufferedReader br = new BufferedReader( new FileReader(file));
		long current_rownum = 0;
		String result="";
		while(current_rownum<rownum){
			br.readLine();
			current_rownum++;
		}
		result = br.readLine();
		br.close();
		return result;
	}
	public static long getCharCount(File file) throws IOException{
		if(file==null){
			return 0;
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		long temp_count = 0;
		long result = 0;
		char[] temp = new char[1024];
		while((temp_count = br.read(temp))>0){
			result+=temp_count;
		}
		br.close();
		return result;
	}
	public static void copy(File file_src,File file_obj) throws IOException{
		if(!file_obj.isFile()){
			file_obj.createNewFile();
		}
		FileInputStream fi = new FileInputStream(file_src);
		FileOutputStream fo = new FileOutputStream(file_obj);
		byte[] temp = new byte[1024];
		while(fi.read(temp)!=-1){
			fo.write(temp);
		}
		fi.close();
		fo.close();
	}
	
	
	public static Map getSentimentalLine(String file, String pattern) throws IOException{
		Map result = new HashMap();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = "";
		long linenum = 1;
		while((temp = br.readLine()) != null){
			if(Pattern.matches(pattern, temp)){
				result.put(linenum, temp);
			}
			linenum++;
		}
		br.close();
		return result;
	}
	
	public static String getLetterAfter(String line,String after){
		if(line.toLowerCase().indexOf(after.toLowerCase())==-1)return null;
		int from =line.toLowerCase().indexOf(after.toLowerCase())+after.length();
		int to =from;
		if(		line.toLowerCase().indexOf(" ", from) != -1
				||line.toLowerCase().indexOf("]", from) != -1 
				||line.toLowerCase().indexOf("\n", from) != -1
				||line.toLowerCase().indexOf("\r\n", from) != -1
				){
		}
		
		return line.substring(from,to+1);
	}
}
