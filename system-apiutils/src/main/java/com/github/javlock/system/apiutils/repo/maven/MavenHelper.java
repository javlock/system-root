package com.github.javlock.system.apiutils.repo.maven;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ExecutorMasterOutputListener;
import com.github.javlock.system.apiutils.os.OsUtils;

public class MavenHelper {
	public static final Logger LOGGER = LoggerFactory.getLogger("MavenHelper");

	public static boolean buildRepo(File repo) throws IOException, InterruptedException {
		String shell = OsUtils.getSystemShell();
		String maven = OsUtils.findProgInSys("mvn");

		int statusMaven = new ExecutorMaster().setOutputListener(new ExecutorMasterOutputListener() {
			@Override
			public void appendInput(String line) {
				LOGGER.info(line);
			}

			@Override
			public void appendOutput(String line) {
				LOGGER.info(line);
			}
		}).parrentCommand(shell).dir(repo).command(maven + " clean install && exit 0 ").call();

		boolean stat = statusMaven == 0;
		if (stat) {
			LOGGER.info("Репозиторий {} собран", repo);
		}
		return stat;
	}
}
