package com.github.javlock.system.updater;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;

public class Updater extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger("Updater");

	private void build() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		int sec = 1000;
		int minute = 60 * sec;
		int min5 = 5 * minute;
		do {
			try {
				if (updateNeeded()) {
					LOGGER.info("Переходим к сборке");
					build();
					update();
				}
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

	private void update() {
		// TODO Auto-generated method stub

	}

	private boolean updateNeeded() throws GitAPIException, IOException {

		try (Git git = Git.open(Paths.repoDir)) {
			PullResult pullResult = git.pull().call();

			if (pullResult.isSuccessful()) {
				LOGGER.info("pullResult.isSuccessful()");
				FetchResult result = pullResult.getFetchResult();

				int index = 0;

				for (TrackingRefUpdate trackingRefUpdate : result.getTrackingRefUpdates()) {
					LOGGER.info("ID:{} msg:{}", index,
							ToStringBuilder.reflectionToString(trackingRefUpdate, ToStringStyle.MULTI_LINE_STYLE));
					index++;
				}
				return !result.getTrackingRefUpdates().isEmpty();
			}
		}

		return false;
	}
}
