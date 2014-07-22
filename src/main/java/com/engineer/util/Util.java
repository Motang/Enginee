package com.engineer.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Util {

	public static Object initObjbyMap(Map map, Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class objClass = obj.getClass();
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next().toString();
			Method[] methods = objClass.getMethods();
			for(int i=0;i<methods.length;i++){
				if(SCUtil.lowerFirstLetter(methods[i].getName().replace("set", "")).indexOf(key)!=-1){
					methods[i].invoke(obj, map.get(key));
				}
			}
		}
		return obj;
	}
	public static Object Map2Object(Map map, Class objClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object result = objClass.newInstance();
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next().toString();
			Method[] methods = objClass.getMethods();
			for(int i=0;i<methods.length;i++){
				if(SCUtil.lowerFirstLetter(methods[i].getName().replace("set", "")).indexOf(key)!=-1){
					methods[i].invoke(result, map.get(key));
				}
			}
		}
		return result;
	}

	public static Map Object2Map(Object obj) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Map result = new HashMap();
		Method[] methods = obj.getClass().getMethods();
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				result.put(
						SCUtil.lowerFirstLetter(methods[i].getName().replace("get", "")),
						methods[i].invoke(obj));
			}
		}
		return result;
	}
	
	public static String Object2Form(Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		StringBuffer result = new StringBuffer();
		Method[] methods = obj.getClass().getMethods();
		result.append("<form action=\""+obj.getClass().getName()+".action\">");
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				result.append("<input type=\"text\" name=\""+obj.getClass().getName()+"."+methods[i].getName()+"\" value=\""+methods[i].invoke(obj)+"\"><br>");
			}
		}
		result.append("</form>");
		return result.toString();
	}
	public static String Class2Form(Class cls){
		StringBuffer result = new StringBuffer();
		Method[] methods = cls.getMethods();
		result.append("<form action=\""+cls.getName()+".action\">");
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().indexOf("get")!=-1){
				result.append("    <input type=\"text\" name=\""+cls.getName()+"."+methods[i].getName()+"\" value=\"\"><br>");
			}
		}
		result.append("</form>");
		return result.toString();
	}
	public static String Map2Select(String name, Map map){
		StringBuffer result = new StringBuffer();
		result.append("<select name=\""+name+"\">");
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String value = iterator.next().toString();
			result.append("    <option value=\""+value+"\">"+map.get(value)+"</option>");
		}
		result.append("</select>");
		return result.toString();
	}
	public static String Map2SelectConvert(String name, Map map){
		StringBuffer result = new StringBuffer();
		result.append("<select name=\""+name+"\">");
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String value = iterator.next().toString();
			result.append("    <option value=\""+map.get(value)+"\">"+value+"</option>");
		}
		result.append("</select>");
		return result.toString();
	}
	
}


