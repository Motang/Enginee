package com.engineer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SCUtil {
	public static void main(String[] args){
		System.out.println(upperFirstLetter("qwqwqwqw"));
		System.out.println(lowerFirstLetter("qwqwqwqw"));
		System.out.println(upperFirstLetter("AAAwqwqwqw"));
		System.out.println(lowerFirstLetter("AAAqwqwqwqw"));
		System.out.println(upperLetters("AAAqwqwqwqw"));
		System.out.println(lowerLetters("AAAqwqwqwqw"));

		System.out.println(getAfterSeparator("sdsdsd|aaaaaaaaa"));
		
		System.out.println(replaceSuffix4Prefix("<%@ dfdfdfdfdfdf /><%@sdsdsd/>    ","<%@","/>","%>"));
	}
	public static void clearUpFolder(String path){
		File folder  =  new File(path);
		if(folder.isDirectory()){
			System.out.println("clear up the distination!");
			String files[] = folder.list();
			System.out.println("totally have "+files.length+" files deleting!");
			for(int i=0;i<files.length;i++){
				new File(files[i]).delete();
				
				if(i%8==0)System.out.println();
				System.out.print(files[i] +" =delete=> ");
			}
		}else{
			System.out.println(path+" is NOT a folder!");
		}
	}
	public static String replaceSuffix4Prefix(String line, String prefix, String suffix, String newSuffix){
		int index = 0;
		while(true){
			index = line.indexOf(prefix, index);
			if (index == -1) break;
			index = line.indexOf(suffix, index);//"/>"just after"<%@"
			line = line.substring(0,index) + newSuffix + line.substring(index+2);
		}
		return line;
	}
	
	
	
	public static List<String> toArrayList(Object[] objs){
		List<String> result = new ArrayList<String>();
		if(objs==null)return null;
		for(int i = 0;i<objs.length;i++){
			result.add(lowerLetters((String)objs[i]));
		}
		return result;
	}
	
	
	public static String getBeforeSeparator(String field){
		if(field==null){
			return "";
		}
		String[] split = field.split("\\|");
		return split[0];
	}
	
	public static String getAfterSeparator(String field){
		if(field==null){
			return "";
		}
		String[] split = field.split("\\|");
		return split[1];
	}
	
	public static String upperFirstLetter(String field){
		if(field==null){
			return "";
		}
		char firstLetter = field.charAt(0);
		firstLetter = Character.toUpperCase(firstLetter);
		return firstLetter+field.substring(1);
	}
	
	public static String lowerFirstLetter(String field){
		if(field==null){
			return "";
		}
		char firstLetter = field.charAt(0);
		firstLetter = Character.toLowerCase(firstLetter);
		return firstLetter+field.substring(1);
	}
	
	public static String upperLetters(String field){
		if(field==null){
			return "";
		}
		char[] letters = field.toCharArray();
		for(int i = 0;i<letters.length;i++){
			letters[i] = Character.toUpperCase(letters[i]);
		}
		return new String(letters);
	}
	
	public static String lowerLetters(String field){
		if(field==null){
			return "";
		}
		char[] letters = field.toCharArray();
		for(int i = 0;i<letters.length;i++){
			letters[i] = Character.toLowerCase(letters[i]);
		}
		return new String(letters);
	}
	
	public static String removeLastLetter(String field){
		return field.substring(0,field.length()-2);
	}
	



	public static String lsp(String content, int length){
		if(content != null && content.length()>length){
			return content.substring(0,length);
		}
		String temp = "";
		if(content != null){
			temp = content + "";
		}
		while(temp.length() < length){
			temp  = " " + temp;
		}

		return temp;
	}
	public static String rsp(String content, int length){
		if(content != null && content.length()>length){
			return content.substring(0,length);
		}
		String temp = "";
		if(content != null){
			temp = content + "";
		}
		while(temp.length() < length){
			temp  = temp + " ";
		}

		return temp;
	}
	public static String lstr(String content, int length, String str){
		if(content != null && content.length()>length){
			return content.substring(0,length);
		}
		String temp = "";
		if(content != null){
			temp = content + "";
		}
		while(temp.length() < length){
			temp  = str + temp;
		}

		return temp;
	}
	public static String rstr(String content, int length,String str){
		if(content != null && content.length()>length){
			return content.substring(0,length);
		}
		String temp = "";
		if(content != null){
			temp = content + "";
		}
		while(temp.length() < length){
			temp  = temp + str;
		}

		return temp;
	}

}
