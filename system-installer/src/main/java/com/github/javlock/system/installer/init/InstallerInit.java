package com.github.javlock.system.installer.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.naming.ConfigurationException;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.DataSets;
import com.github.javlock.system.apidata.Paths;
import com.github.javlock.system.apidata.systemd.data.ServicesDemoManual;
import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ServicesJavLock;
import com.github.javlock.system.apiutils.os.OsUtils;
import com.github.javlock.system.apiutils.repo.git.GitHelper;
import com.github.javlock.system.apiutils.repo.maven.MavenHelper;
import com.github.javlock.system.installer.config.InstallerConfig;

public class InstallerInit {

	public static final SecureRandom RANDOM = new SecureRandom();

	public static final Logger LOGGER = LoggerFactory.getLogger("INSTALLER");

	public static String generatePassword(int len, String dic) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int index = RANDOM.nextInt(dic.length());
			result.append(dic.charAt(index));
		}
		return result.toString();
	}

	public static void main(String[] args) {
		try {
			InstallerInit init = new InstallerInit();
			readVars(init, args);
			init.init();
			init.install();
			init.test();
			init.exit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void printHelp() {// FIXME дописать/исправить
		StringBuilder builder = new StringBuilder();
		builder.append('\n');

		// git repo
		builder.append("--no-git-ops for start install without operations with git repository").append('\n');
		builder.append("--dev for start install dev version").append('\n');

		// logs
		builder.append("--debug for start install with debug messages").append('\n');

		// prepare
		builder.append("--ok after set needed args , for продолжения установки").append('\n');

		builder.append('\n').append('\n').append('\n');

		builder.append("Exit statuses:").append('\n');

		builder.append(1).append(" For:1").append('\n');
		builder.append(2).append(" For: Не удалось получить/обновить репозиторий").append('\n');
		builder.append(3).append(" For: Args check: args is empty (args[].length == 0)").append('\n');
		builder.append(4).append(" For: OS check: you OS is not supported").append('\n');
		builder.append(5).append(" For: User check: you no root user").append('\n');
		builder.append(6).append(" For: Не удалось собрать репозиторий").append('\n');
		builder.append(7).append(" For:7").append('\n');
		builder.append(8).append(" For:8").append('\n');
		builder.append(9).append(" For:У").append('\n');

		LOGGER.info(builder.toString());
	}

	private static void readVars(InstallerInit init, String[] args) throws ConfigurationException {
		if (args.length == 0) {
			printHelp();
			Runtime.getRuntime().exit(3);
		}
		for (String arg : args) {

			if (arg.equalsIgnoreCase("--dev")) {
				init.config.setVersion(DataSets.VERSIONTYPE.DEV);
			}

			if (arg.equalsIgnoreCase("--no-git-ops")) {
				init.config.setGitOps(false);
			}
			if (arg.equalsIgnoreCase("--ok")) {
				init.config.setPrepare(true);
			}

			if (arg.equalsIgnoreCase("--debug")) {
				init.debug = true;
			}
		}
		LOGGER.info("\n{}", init.config.toString());
		if (!init.config.isPrepare()) {
			// FIXME дописать
			throw new ConfigurationException("You not set arg after configurat***: --ok");
		}
	}

	InstallerConfig config = new InstallerConfig();

	private boolean debug = false;

	private void checkUser(boolean deb) {
		String user = SystemUtils.USER_NAME;
		LOGGER.info("your user {}", user);
		if (user.equals("root")) {
		} else {
			if (!deb) {
				LOGGER.error("Установка прервана: вы не root");
				Runtime.getRuntime().exit(5);
			} else {
				LOGGER.warn("Установка продолжена: тк включена отладка");
			}
		}
	}

	private String createServiceFor(File repository, String moduleName) throws IOException {
		StringBuilder builder = new StringBuilder();
		System.err.println(moduleName);
		// File executableJarFile =
		// System.out.println("InstallerInit.createServiceFor()" +
		// executableJarFile.getAbsolutePath());
		return builder.toString();
	}

	private void createServices() throws IOException, InterruptedException {
		File servicesDir = ServicesJavLock.findServicesDir();
		LOGGER.info("Найден путь до сервисов {}", servicesDir);

		// updater
		ArrayList<File> jars = new ArrayList<>();
		findJarWithDeps(jars, Paths.repoDir.getAbsolutePath());
		for (File file : jars) {
			LOGGER.info("JARRRS:{}", file);
		}
		Files.writeString(new File(servicesDir, "javlock-system-updater.service").toPath(),
				ServicesDemoManual.UPDATERSERVICEDATA, StandardOpenOption.TRUNCATE_EXISTING);

		// TODO rewrite writeServiceFor(servicesDir, "system-updater");
		// TODO rewrite writeServiceFor(servicesDir, "system-kernel");

		String shell = OsUtils.getSystemShell();
		// update daemon

		String cmd1 = "systemctl daemon-reload";
		String cmd2 = "systemctl enable javlock-system-updater";
		String cmd3 = "systemctl restart javlock-system-updater";
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd1).call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd2).call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command(cmd3).call();

		// install
		// new
		// ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl
		// daemon-reload").call();
		// new
		// ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl
		// daemon-reload").call();
		// new
		// ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl
		// daemon-reload").call();

	}

	private void exit() {

	}

	private void findJarWithDeps(ArrayList<File> jars, String repository) throws IOException {
		File root = new File(repository);
		File[] list = root.listFiles();
		if (list == null) {
			return;
		}
		for (File f : list) {
			if (f.isDirectory()) {
				findJarWithDeps(jars, f.getAbsolutePath());
			} else {
				String name = f.getName().toLowerCase();
				if (name.endsWith(Paths.exeJarSuffix)) {
					jars.add(f);
				}
			}
		}
	}

	private void init() {

		String os = SystemUtils.OS_NAME;
		String arch = SystemUtils.OS_ARCH;

		if (SystemUtils.IS_OS_LINUX) {
			LOGGER.info("install for {} {}", os, arch);
		} else {
			LOGGER.error("{} is not supported", os);
			Runtime.getRuntime().exit(4);
		}

		checkUser(debug);

	}

	private void install() throws IOException, InterruptedException {
		String branch = config.getVersion().toString().toLowerCase();

		if (config.isGitOps()) {
			if (GitHelper.getRepo(Paths.repoUrl, Paths.repoDir, branch)) {
				LOGGER.info("Проверка git репозитория успешно завершена");
			} else {
				LOGGER.error("Проверка git репозитория не завершена");
				Runtime.getRuntime().exit(2);
			}
		} else {
			LOGGER.warn("Указан --no-git-ops  Пропускаем обновление git репозитория");
		}

		if (MavenHelper.buildRepo(Paths.repoDir)) {
			LOGGER.info("Сборка репозитория успешно завершена");
		} else {
			LOGGER.error("Сборка репозитория не завершена");
			Runtime.getRuntime().exit(6);
		}

		createServices();
	}

	private void test() {
		// TODO написать тесты для отладки сервисов после установки
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	private void writeServiceFor(File servicesDir, String moduleName) throws IOException {
		// TODO переписать для использования модуля system-systemd
		File serviceFile = new File(servicesDir, "javlock-" + moduleName + ".service");
		if (!serviceFile.exists()) {
			LOGGER.info("File {} created:{}", serviceFile, serviceFile.createNewFile());
		}
		String serviceData = createServiceFor(Paths.repoDir, moduleName);
		Files.write(serviceFile.toPath(), serviceData.getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.TRUNCATE_EXISTING);
	}
}
