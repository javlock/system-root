package com.github.javlock.system.installer.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.installer.config.InstallerConfig;
import com.github.javlock.system.installer.utils.ExecutorMaster;
import com.github.javlock.system.installer.utils.ExecutorMasterOutputListener;

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
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void readVars(InstallerInit init, String[] args) {
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--gui")) {
				init.mode = INSTALLERMode.GUI;
			}
			if (arg.equalsIgnoreCase("--debug")) {
				init.debug = true;
			}
			if (arg.equalsIgnoreCase("--dev")) {
				init.config.setVersion(VERSIONTYPE.DEV);
			}
		}
	}

	InstallerConfig config = new InstallerConfig();

	INSTALLERMode mode = INSTALLERMode.GUILESS;
	private boolean debug = false;
	File repoDir = new File(new File("/", "opt"), "javlock-system");

	String repoUrl = "https://github.com/javlock/system-root";

	String shell = null;

	private int buildRepo() throws IOException, InterruptedException {
		try {
			shell = findProgInSys("bash");
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (shell == null) {
			shell = findProgInSys("sh");
		}
		String maven = findProgInSys("mvn");

		return new ExecutorMaster().setOutputListener(new ExecutorMasterOutputListener() {
			@Override
			public void appendInput(String line) {
				LOGGER.info(line);
			}

			@Override
			public void appendOutput(String line) {
				LOGGER.info(line);
			}
		}).parrentCommand(shell).dir(repoDir).command(maven + " clean install && exit 0 ").call();
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
		findJarWithDeps(jars, repoDir.getAbsolutePath());
		for (File file : jars) {
			LOGGER.info("JARRRS:{}", file);
		}

		writeServiceFor(servicesDir, "system-updater");
		writeServiceFor(servicesDir, "system-kernel");

		// update daemon
		new ExecutorMaster().parrentCommand(shell).dir(repoDir).command("systemctl daemon-reload").call();

		// install
		new ExecutorMaster().parrentCommand(shell).dir(repoDir).command("systemctl daemon-reload").call();
		new ExecutorMaster().parrentCommand(shell).dir(repoDir).command("systemctl daemon-reload").call();
		new ExecutorMaster().parrentCommand(shell).dir(repoDir).command("systemctl daemon-reload").call();

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

	private String findProgInSys(String name) throws FileNotFoundException {
		final String PATH = System.getenv("PATH");
		String[] ar = PATH.split(":");
		for (String string : ar) {
			File testFile = new File(string, name);
			if (testFile.exists()) {
				return testFile.getAbsolutePath();
			}
		}
		throw new FileNotFoundException("programm with name " + name + " not found");
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
		if (repoDir.exists()) {
			try (Git git = Git.open(repoDir);) {
				git.pull().call();
			}
		} else {
			Git.cloneRepository().setURI(repoUrl).setDirectory(repoDir)
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
		LOGGER.info(config.toString());
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
		String serviceData = createServiceFor(repoDir, moduleName);
		Files.write(serviceFile.toPath(), serviceData.getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.TRUNCATE_EXISTING);
	}
}
