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

import com.github.javlock.system.apidata.Paths;
import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.os.OsUtils;
import com.github.javlock.system.apiutils.repo.git.GitHelper;
import com.github.javlock.system.apiutils.repo.maven.MavenHelper;
import com.github.javlock.system.installer.config.InstallerConfig;
import com.github.javlock.system.installer.gui.InstallerGui;

public class InstallerInit {

	public enum INSTALLERMode {
		/**
		 * Open installer with gui
		 */
		GUI,
		/**
		 * Open installer without gui
		 */
		GUILESS
	}

	public enum VERSIONTYPE {
		MAIN, DEV
	}

	public static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * ALPHA_CAPS
	 */
	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * ALPHA
	 */
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * NUMERIC
	 */
	public static final String NUMERIC = "0123456789";
	/**
	 * SPECIAL_CHARS
	 */
	public static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

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

	private static void printHelp() {
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		builder.append("--gui for start Info Frame").append('\n');
		builder.append("--dev for start install dev version").append('\n');
		builder.append("--debug for start install with debug messages").append('\n');
		builder.append("--ok after set needed args , for продолжения установки").append('\n');// FIXME дописать
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
		builder.append(9).append(" For:9").append('\n');

		LOGGER.info(builder.toString());
	}

	private static void readVars(InstallerInit init, String[] args) throws ConfigurationException {
		if (args.length == 0) {
			printHelp();
			Runtime.getRuntime().exit(3);
		}
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--gui")) {
				init.mode = INSTALLERMode.GUI;
			}
			if (arg.equalsIgnoreCase("--dev")) {
				init.config.setVersion(VERSIONTYPE.DEV);
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

	INSTALLERMode mode = INSTALLERMode.GUILESS;
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

	// TODO /usr/lib/systemd/system/
	private void createServices() throws IOException, InterruptedException {
		File servicesDir = findServicesDir();

		// updater
		ArrayList<File> jars = new ArrayList<>();
		findJarWithDeps(jars, Paths.repoDir.getAbsolutePath());
		for (File file : jars) {
			LOGGER.info("JARRRS:{}", file);
		}

		writeServiceFor(servicesDir, "system-updater");
		writeServiceFor(servicesDir, "system-kernel");

		String shell = OsUtils.getSystemShell();
		// update daemon
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl daemon-reload").call();

		// install
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl daemon-reload").call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl daemon-reload").call();
		new ExecutorMaster().parrentCommand(shell).dir(Paths.repoDir).command("systemctl daemon-reload").call();

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
				if (name.endsWith("-jar-with-dependencies.jar")) {
					jars.add(f);
				}

			}
		}
	}

	private File findServicesDir() throws FileNotFoundException {

		File ret = null;

		if (Paths.systemdDir1.exists()) {
			ret = Paths.systemdDir1;
		}
		if (Paths.systemdDir2.exists()) {
			ret = Paths.systemdDir2;
		}

		if (ret != null) {
			LOGGER.info("Найден путь до сервисов {}", ret);
		} else {
			throw new FileNotFoundException("Не найден путь до сервисов");
		}
		return ret;
	}

	private void init() {
		if (mode == INSTALLERMode.GUI) {
			InstallerGui gui = new InstallerGui();
			gui.setVisible(true);
		}

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

		if (GitHelper.getRepo(Paths.repoUrl, Paths.repoDir, branch)) {
			LOGGER.info("Проверка git репозитория успешно завершена");
		} else {
			LOGGER.error("Проверка git репозитория не завершена");
			Runtime.getRuntime().exit(2);
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
