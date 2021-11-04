package com.github.javlock.system.updater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;

public class Updater extends Thread {
	private File repoDir = new File("/opt/javlock-system");

	@Override
	public void run() {
		int sec = 1000;
		int minute = 60 * sec;

		int sec10 = 10 * sec;
		int min5 = 5 * minute;
		do {
			try {

				if (updateNeeded()) {
					update();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				long delay = sec10;
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	private void update() {

	}

	private boolean updateNeeded() throws WrongRepositoryStateException, InvalidConfigurationException,
			InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException,
			TransportException, GitAPIException, IOException {

		PullResult pullResult = Git.open(repoDir).pull().call();

		System.err.println(pullResult);

		System.err.println(ToStringBuilder.reflectionToString(pullResult, ToStringStyle.MULTI_LINE_STYLE));

		System.err.println(pullResult.isSuccessful());

		if (pullResult.isSuccessful()) {
			System.err.println("TEEEEEEEEEEEEEEEEEEEE");
		}

		return false;
	}
}
