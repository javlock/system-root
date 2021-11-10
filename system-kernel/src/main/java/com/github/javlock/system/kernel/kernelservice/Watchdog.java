package com.github.javlock.system.kernel.kernelservice;

import java.util.concurrent.CopyOnWriteArrayList;

public class Watchdog extends Thread {
	CopyOnWriteArrayList<Thread> threads = new CopyOnWriteArrayList<>();
	boolean active = true;

	public void register(Thread thread) {
		threads.add(thread);
	}

	@Override
	public void run() {
		do {
			for (Thread thread : threads) {
				State state = thread.getState();
				Class<? extends Thread> clss = thread.getClass();

				switch (state) {

				case NEW -> {// ignore
				}
				case BLOCKED -> {// ignore
				}
				case RUNNABLE -> {// ignore
				}
				case TERMINATED -> {
				}
				case TIMED_WAITING -> {// ignore
					System.out.println("Watchdog.run():" + thread);
				}
				case WAITING -> {// ignore
				}

				default -> throw new IllegalArgumentException("Unexpected value: " + state);
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (active);
	}

	public void stopAll() {
		// TODO Auto-generated method stub

	}

	public void stopSelf() {
		active = false;
	}

	public void unRegister(Thread thread) {
		threads.remove(thread);
	}

	public void unRegisterAll() {
		for (Thread thread : threads) {
			unRegister(thread);
		}
	}

}
