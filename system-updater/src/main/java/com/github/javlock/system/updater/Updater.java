package com.github.javlock.system.updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apiutils.repo.RepoUtils;

public class Updater extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger("Updater");

	@Override
	public void run() {
		int sec = 1000;
		int minute = 60 * sec;
		int min5 = 5 * minute;
		do {
			try {
				RepoUtils.fullCase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long delay = 0;
			try {
				delay = min5;
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				LOGGER.error("sleep err with time:{}", delay, e);
				Thread.currentThread().interrupt();
			}
		} while (true);
	}

}
