package com.github.javlock.system.updater;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
import com.github.javlock.system.apidata.systemd.data.ServicesDemoManual;
import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ServicesJavLock;
import com.github.javlock.system.apiutils.os.OsUtils;
import com.github.javlock.system.apiutils.repo.maven.MavenHelper;

public class Updater extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger("Updater");

	private boolean build() throws IOException, InterruptedException {
		if (MavenHelper.buildRepo(Paths.repoDir)) {
			LOGGER.info("Репозиторий {} собран", Paths.repoDir);
			return true;
		}
		return false;
	}

	private void closeUpdater() {
		LOGGER.info("Выходим");
		Runtime.getRuntime().exit(0);
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
					if (build()) {
						if (update()) {
							closeUpdater();
						}

					}
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

	private boolean update() throws IOException, InterruptedException {
		LOGGER.info("Обновляем файл программы обновления");
		File servicesDir = ServicesJavLock.findServicesDir();
		LOGGER.info("Найден путь до сервисов {}", servicesDir);

		Files.writeString(new File(servicesDir, "javlock-system-updater.service").toPath(),
				ServicesDemoManual.UPDATERSERVICEDATA, StandardOpenOption.TRUNCATE_EXISTING);
		LOGGER.info("Записано");

		LOGGER.info("Получаем shell...");
		String shell = OsUtils.getSystemShell();
		LOGGER.info("shell получен:[{}]", shell);

		LOGGER.info("перезапускаем в systemd...");
		String cmd1 = "systemctl daemon-reload";
		String cmd2 = "systemctl enable javlock-system-updater";
		String cmd3 = "systemctl restart javlock-system-updater";
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd1).call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd2).call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd3).call();
		LOGGER.info("перезапустили");
		return true;
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
