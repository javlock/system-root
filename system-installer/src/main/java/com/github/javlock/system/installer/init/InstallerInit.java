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
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;
import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ExecutorMasterOutputListener;
import com.github.javlock.system.apiutils.os.OsUtils;
import com.github.javlock.system.installer.config.InstallerConfig;

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

	public static SecureRandom random = new SecureRandom();;

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
			int index = random.nextInt(dic.length());
			result.append(dic.charAt(index));
		}
		return result.toString();
	}

	public static void main(String[] args) {
		try {
			InstallerInit init = new InstallerInit();
			readVars(init, args);
			init.printConfig();
			init.init();
			init.install();
			init.test();
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void readVars(InstallerInit init, String[] args) throws ConfigurationException {
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
		LOGGER.info(init.config.toString());
		if (!init.config.isPrepare()) {
			throw new ConfigurationException("");
		}
	}

	InstallerConfig config = new InstallerConfig();

	INSTALLERMode mode = INSTALLERMode.GUILESS;
	private boolean debug = false;

	String shell = null;

	private int buildRepo() throws IOException, InterruptedException {
		shell = OsUtils.getSystemShell();
		String maven = OsUtils.findProgInSys("mvn");

		return new ExecutorMaster().setOutputListener(new ExecutorMasterOutputListener() {
			@Override
			public void appendInput(String line) {
				LOGGER.info(line);
			}

			@Override
			public void appendOutput(String line) {
				LOGGER.info(line);
			}
		}).parrentCommand(shell).dir(Paths.repoDir).command(maven + " clean install && exit 0 ").call();
	}

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
		// return null;
	}

	private File findServicesDir() throws FileNotFoundException {
		File dir1 = new File("/", "usr/lib/systemd/system/");
		File dir2 = new File("/", "etc/systemd/system/");

		File ret = null;

		if (dir1.exists()) {
			ret = dir1;
		}
		if (dir2.exists()) {
			ret = dir2;
		}

		if (ret != null) {
			LOGGER.info("Найден путь до сервисов {}", ret);
		} else {
			throw new FileNotFoundException("Не найден путь до сервисов");

		}
		return ret;
	}

	private void getRepo() throws GitAPIException, IOException {
		if (Paths.repoDir.exists()) {
			try (Git git = Git.open(Paths.repoDir);) {
				git.pull().call();
			}
		} else {
			Git.cloneRepository().setURI(Paths.repoUrl).setDirectory(Paths.repoDir)
					.setBranch(config.getVersion().toString().toLowerCase()).setCloneAllBranches(true).call();
		}
	}

	private void init() {
		String os = SystemUtils.OS_NAME;
		String arch = SystemUtils.OS_ARCH;

		if (SystemUtils.IS_OS_LINUX) {

		} else {
			LOGGER.info("{} is not supported", os);
			Runtime.getRuntime().exit(4);
		}
		checkUser(debug);

	}

	private void install() throws GitAPIException, IOException, InterruptedException {
		getRepo();
		if (buildRepo() == 0) {
			LOGGER.info("Сборка репозитория успешно завершена");
		} else {
			LOGGER.info("Сборка репозитория не завершена");
		}

		createServices();
	}

	private void printConfig() {
	}

	private void test() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	private void writeServiceFor(File servicesDir, String moduleName) throws IOException {
		File serviceFile = new File(servicesDir, "javlock-" + moduleName + ".service");
		if (!serviceFile.exists()) {
			LOGGER.info("File {} created:{}", serviceFile, serviceFile.createNewFile());
		}
		String serviceData = createServiceFor(Paths.repoDir, moduleName);
		Files.write(serviceFile.toPath(), serviceData.getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.TRUNCATE_EXISTING);
	}
}
