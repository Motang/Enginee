package com.engineer.concurrent.common;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

public class TestConcurrentSkipListMap {
	public class Contact{
		private String name;
		private String phone;
		public Contact(String name, String phone) {
			super();
			this.name = name;
			this.phone = phone;
		}
		public String getName() {
			return name;
		}
		public String getPhone() {
			return phone;
		}
		@Override
		public String toString() {
			return "Contact [name=" + name + ", phone=" + phone + "]";
		}
		
	}
	
	public class ContactTask implements Runnable{
		
		private String id;
		private ConcurrentSkipListMap<String, Contact> map;

		public ContactTask(String id, ConcurrentSkipListMap<String, Contact> map) {
			super();
			this.id = id;
			this.map = map;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				String key = id + i;
				Contact value = new Contact(key, "phone"+i);
				map.put(key, value);
			}
			
		}
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConcurrentSkipListMap<String, Contact> map = new ConcurrentSkipListMap<String, Contact>();
		TestConcurrentSkipListMap test = new TestConcurrentSkipListMap();
		for (char i = 'Z'; i >= 'A'; i--) {
			ContactTask contactTask = test.new ContactTask(String.valueOf(i), map);
			Thread thread = new Thread(contactTask);
			thread.start();
		}
		
		ContactTask contactTask = test.new ContactTask(String.valueOf('A'), map);
		Thread thread = new Thread(contactTask);
		thread.start();
		
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Entry<String, Contact> firstEntry = map.firstEntry();
		System.out.printf("Key:%s, value:%s", firstEntry.getKey(), firstEntry.getValue());
		
	}

}
