package com.github.javlock.system.kernel;

import com.github.javlock.system.kernel.kernelservice.ProcessHandler;
import com.github.javlock.system.kernel.kernelservice.Watchdog;

public class Kernel extends Thread {
	Watchdog watchdog = new Watchdog();
	ProcessHandler processHandler = new ProcessHandler();

	boolean active = true;

	public void init() {
		watchdog.register(this);
		watchdog.register(processHandler);
	}

	@Override
	public void run() {
		try {
			Thread.currentThread().setName("Kernel");
			processHandler.start();
			watchdog.start();
			do {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (active);
			watchdog.unRegister(this);
			watchdog.unRegisterAll();
			watchdog.stopAll();
			watchdog.stopSelf();
			watchdog.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
