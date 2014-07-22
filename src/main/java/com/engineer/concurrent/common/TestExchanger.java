package com.engineer.concurrent.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 消费者开始时是空白的buffer，然后调用Exchanger来与生产者同步。因为它需要数据来消耗。生产者也是从空白的buffer开始，然后创建10个字符串
 * ，保存到buffer，并使用exchanger与消费者同步。
 * 在这儿，2个线程(生产者和消费者线程)都是在Exchanger里并交换了数据类型，所以当消费者从exchange()
 * 方法返回时，它有10个字符串在buffer内。当生产者从 exchange() 方法返回时，它有空白的buffer来重新写入。这样的操作会重复10遍。
 * 如你执行例子，你会发现生产者和消费者是如何并发的执行任务和在每个步骤它们是如何交换buffers的。与其他同步工具一样会发生这种情况，第一个调用
 * exchange()方法会进入休眠直到其他线程的达到。
 */
public class TestExchanger {
	// 1. 首先，从实现producer开始吧。创建一个类名为Producer并一定实现 Runnable 接口。
	public class Producer implements Runnable {

		// 2. 声明 List<String>对象，名为 buffer。这是等等要被相互交换的数据类型。
		private List<String> buffer;

		// 3. 声明 Exchanger<List<String>>; 对象，名为exchanger。这个 exchanger
		// 对象是用来同步producer和consumer的。
		private final Exchanger<List<String>> exchanger;

		// 4. 实现类的构造函数，初始化这2个属性。
		public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
			this.buffer = buffer;
			this.exchanger = exchanger;
		}

		// 5. 实现 run() 方法. 在方法内，实现10次交换。
		@Override
		public void run() {
			int cycle = 1;
			for (int i = 0; i < 10; i++) {
				System.out.printf("Producer: Cycle %d\n", cycle);

				// 6. 在每次循环中，加10个字符串到buffer。
				for (int j = 0; j < 10; j++) {
					String message = "Event " + ((i * 10) + j);
					System.out.printf("Producer: %s\n", message);
					buffer.add(message);
				}

				// 7. 调用 exchange()方法来与consumer交换数据。此方法可能会抛出InterruptedException
				// 异常, 加上处理代码。
				try {
					buffer = exchanger.exchange(buffer);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Producer: " + buffer.size());
				cycle++;
			}
		}
	}

	public class Consumer implements Runnable {

		// 9. 声明名为buffer的 List<String>对象。这个对象类型是用来相互交换的。
		private List<String> buffer;

		// 10. 声明一个名为exchanger的 Exchanger<List<String>> 对象。用来同步
		// producer和consumer。
		private final Exchanger<List<String>> exchanger;

		// 11. 实现类的构造函数，并初始化2个属性。
		public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
			this.buffer = buffer;
			this.exchanger = exchanger;
		}

		// 12. 实现 run() 方法。在方法内，实现10次交换。
		@Override
		public void run() {
			int cycle = 1;
			for (int i = 0; i < 10; i++) {
				System.out.printf("Consumer: Cycle %d\n", cycle);

				// 13.
				// 在每次循环，首先调用exchange()方法来与producer同步。Consumer需要消耗数据。此方法可能会抛出InterruptedException异常,
				// 加上处理代码。
				try {
					buffer = exchanger.exchange(buffer);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 14.
				// 把producer发来的在buffer里的10字符串写到操控台并从buffer内删除，留空。System.out.println("Consumer: "
				// + buffer.size());
				for (int j = 0; j < 10; j++) {
					String message = buffer.get(0);
					System.out.println("Consumer: " + message);
					buffer.remove(0);
				}
				cycle++;
			}
		}
	}

	public static void main(String[] args) {
		TestExchanger test = new TestExchanger();

		// 16. 创建2个buffers。分别给producer和consumer使用.
		List<String> buffer1 = new ArrayList<String>();
		List<String> buffer2 = new ArrayList<String>();

		// 17. 创建Exchanger对象，用来同步producer和consumer。
		Exchanger<List<String>> exchanger = new Exchanger<List<String>>();

		// 18. 创建Producer对象和Consumer对象。
		Producer producer = test.new Producer(buffer1, exchanger);
		Consumer consumer = test.new Consumer(buffer2, exchanger);

		// 19. 创建线程来执行producer和consumer并开始线程。
		Thread threadProducer = new Thread(producer);
		Thread threadConsumer = new Thread(consumer);

		threadProducer.start();
		threadConsumer.start();

		try {
			threadProducer.join();
			threadConsumer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
