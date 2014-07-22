package com.engineer.concurrent.common;

public class TestSynchronized {
	public class Account {
		private double balance;
		private double mount;
		private double umount;

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public double getMount() {
			return mount;
		}

		public void setMount(double mount) {
			this.mount = mount;
		}

		public double getUmount() {
			return umount;
		}

		public void setUmount(double umount) {
			this.umount = umount;
		}

		public synchronized void addAmount(double amount) {
			double tmp = balance;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//tmp += amount;
			//balance = tmp;
			balance += amount;
			mount += amount;
			//umount += amount;
		}

		public synchronized void subtractAmount(double amount) {
			double tmp = balance;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//tmp -= amount;
			//balance = tmp;
			balance -= amount;
			umount -= amount;
			//mount -= amount;
		}
	}

	public class Company implements Runnable {
		private Account account;

		public Company(Account account) {
			super();
			this.account = account;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				account.addAmount(1000);
			}
		}
	}

	public class Bank implements Runnable {
		private Account account;

		public Bank(Account account) {
			super();
			this.account = account;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				account.subtractAmount(1000);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestSynchronized testSynchronized = new TestSynchronized();
		Account account = testSynchronized.new Account();

		Thread companyThread1 = new Thread(testSynchronized.new Company(account));
		Thread companyThread2 = new Thread(testSynchronized.new Company(account));
		Thread bankThread1 = new Thread(testSynchronized.new Bank(account));
		Thread bankThread2 = new Thread(testSynchronized.new Bank(account));

		System.out.printf("Account : Initial Balance: %f, %f, %f\n", account.getBalance(), account.getMount(), account.getUmount());

		companyThread1.start();
		bankThread1.start();
		companyThread2.start();
		bankThread2.start();

		try {
			companyThread1.join();
			bankThread1.join();
			companyThread2.join();
			bankThread2.join();
			System.out.printf("Account : Final Balance: %f, %f, %f\n", account.getBalance(), account.getMount(), account.getUmount());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
