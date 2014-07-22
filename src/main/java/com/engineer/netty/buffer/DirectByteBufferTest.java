package com.engineer.netty.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import sun.nio.ch.DirectBuffer;

public class DirectByteBufferTest {

	public static void main(String[] args) throws Exception {
		TimeUnit.SECONDS.sleep(10);
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 100);
		System.out.println("start");
		TimeUnit.SECONDS.sleep(20);
		clean(buffer);
		System.out.println("end");
		TimeUnit.SECONDS.sleep(20);
	}

	private static void clean(ByteBuffer byteBuffer) {
		if (byteBuffer.isDirect()) {
			((DirectBuffer) byteBuffer).cleaner().clean();
		}
	}

}
