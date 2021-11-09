package com.github.javlock.system.apiutils.repo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;
import com.github.javlock.system.apidata.systemd.data.ServicesDemoManual;
import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ServicesJavLock;
import com.github.javlock.system.apiutils.os.OsUtils;
import com.github.javlock.system.apiutils.repo.maven.MavenHelper;

public class RepoUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger("RepoUtils");

	private static boolean build() throws IOException, InterruptedException {
		if (MavenHelper.buildRepo(Paths.repoDir)) {
			LOGGER.info("Репозиторий {} собран", Paths.repoDir);
			return true;
		}
		return false;
	}

	public static void buildMoveUpdate() throws IOException, InterruptedException {
		if (build()) {
			if (movedExecsJarsToPersDir()) {
				if (update()) {
					restart();
				}
			}
		}

	}

	private static boolean movedExecsJarsToPersDir() throws IOException {
		ArrayList<File> jars = new ArrayList<>();
		RepoFiles.findJarWithDeps(jars, Paths.repoDir.getAbsolutePath());
		File newDir = Paths.repoDirJars;
		if (!newDir.exists()) {
			newDir.mkdirs();
		}

		String updaterPREF = "system-updater";
		File updaterNewFile = new File(newDir, updaterPREF + ".jar");

		String kernelPREF = "system-updater";
		File kernelNewFile = new File(newDir, kernelPREF + ".jar");

		for (File file : jars) {
			LOGGER.info("Найден исполняемый файл:{}", file);

			String fileName = file.getName();
			String fileNameLC = fileName.toLowerCase();
			if (fileNameLC.startsWith(updaterPREF.toLowerCase()) && fileNameLC.endsWith(Paths.exeJarSuffix)) {
				if (!file.renameTo(updaterNewFile)) {
					LOGGER.error("{} не перемещен в {}", updaterPREF, newDir);
					return false;
				}
			}
			if (fileNameLC.startsWith(kernelPREF.toLowerCase()) && fileNameLC.endsWith(Paths.exeJarSuffix)) {
				if (!file.renameTo(kernelNewFile)) {
					LOGGER.error("{} не перемещен в {}", kernelPREF, newDir);
					return false;
				}
			}
		}
		return true;
	}

	private static void restart() throws IOException, InterruptedException {
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

	}

	private static boolean update() throws IOException, InterruptedException {
		try {
			LOGGER.info("Обновляем файл программы обновления");
			File servicesDir = ServicesJavLock.findServicesDir();
			LOGGER.info("Найден путь до сервисов {}", servicesDir);

			Files.writeString(new File(servicesDir, "javlock-system-updater.service").toPath(),
					ServicesDemoManual.UPDATERSERVICEDATA, StandardOpenOption.TRUNCATE_EXISTING);
			LOGGER.info("Записано");
		} catch (Exception e) {
			LOGGER.error("Ошибка", e);
			return false;
		}
		return true;
	}

}
