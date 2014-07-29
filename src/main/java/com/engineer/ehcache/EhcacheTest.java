package com.engineer.ehcache;

import java.net.URL;

import junit.framework.Assert;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.util.ClassLoaderUtil;

import org.junit.Before;
import org.junit.Test;

public class EhcacheTest {
	String configFile = "config/ehcache.xml";
	String configFile1 = "config/ehcache1.xml";
	String configFile2 = "config/ehcache2.xml";
	
	@Test
	public void testCache(){
		URL url = getURL(configFile);
		CacheManager manager = CacheManager.newInstance(url);
		manager.addCache("testCache");
		Cache test = manager.getCache("testCache");
		Element element = new Element("key", "value");
		test.put(element);
		
		Assert.assertEquals(test.get("key"), element);
	}
	
	//@Before
	public void init() {
		URL url1 = getURL(configFile1);
		CacheManager manager1 = CacheManager.newInstance(url1);

		manager1.addCache("testCache1");
		Cache test1 = manager1.getCache("testCache1");
		//System.out.println(test1.get("key1"));
		test1.put(new Element("key1", "value1"));
		
		URL url2 = getURL(configFile2);
		CacheManager manager2 = CacheManager.newInstance(url2);
		
		manager2.addCache("testCache2");
		Cache test2 = manager2.getCache("testCache2");
		test2.put(new Element("key2", "value2"));
		
		//flush to Disk
		test1.flush();
		test2.flush();
		
		manager1.shutdown();
		manager2.shutdown();
		
	}


	//@Test
	public void testGetAgain() {
		CacheManager manager11 = CacheManager.newInstance(getURL(configFile1));
		manager11.addCache("testCache1");
		Cache test1 = manager11.getCache("testCache1");
		Element value1 = test1.get("key1");
		Assert.assertEquals(value1.getObjectValue(), "value1");
		
		CacheManager manager22 = CacheManager.newInstance(getURL(configFile2));
		manager22.addCache("testCache2");
		Cache test2 = manager22.getCache("testCache2");
		Element value2 = test2.get("key2");
		Assert.assertEquals(value2.getObjectValue(), "value2");
		
		manager11.shutdown();
		manager22.shutdown();
	}

	private URL getURL(String config_file) {
		ClassLoader standardClassloader = ClassLoaderUtil.getStandardClassLoader();
		URL url = null;
		if (standardClassloader != null) {
			url = standardClassloader.getResource(config_file);
		}

		if (url == null) {
			url = ConfigurationFactory.class.getResource(config_file);
		}
		return url;
	}
}
