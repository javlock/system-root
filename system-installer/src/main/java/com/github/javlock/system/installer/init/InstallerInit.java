package com.github.javlock.system.installer.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

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

	public static SecureRandom random = new SecureRandom();
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
		}
	}

	InstallerConfig config = new InstallerConfig();

	INSTALLERMode mode = INSTALLERMode.GUILESS;

	private boolean debug = false;

	File repoDir = new File("/opt/javlock-system");
	String repoUrl = "https://github.com/javlock/system-root";

	private void buildRepo() throws FileNotFoundException {
		String bash = findProgInSys("bash");
		String maven = findProgInSys("mvn");

		executeProgramms(bash, "cd " + repoDir.getAbsolutePath() + ";pwd;", maven + " clean install && exit");

	}

	private void checkUser(boolean deb) {
		String user = SystemUtils.USER_NAME;
		System.err.println("Пользователь " + user);
		if (user.equals("root")) {
		} else {
			if (!deb) {
				System.err.println("Установка прервана: вы не root");
				Runtime.getRuntime().exit(5);
			} else {
				System.err.println("Установка продолжена: тк включена отладка");
			}
		}
	}

	private void executeProgramms(String... progs) {
		String parentProg = progs[0];
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(parentProg);
			processBuilder.redirectErrorStream(true);

			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			Thread commandAppender = new Thread((Runnable) () -> {
				OutputStream os = process.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);

				for (int i = 1; i < progs.length; i++) {
					String string = progs[i];
					try {
						osw.append(string).append('\n');
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					osw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}, parentProg);
			commandAppender.start();
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
			commandAppender.join();
			System.out.println("\nExited with error code : " + exitCode);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	private void getRepo() throws GitAPIException {
		if (!repoDir.exists()) {
			Git git = Git.cloneRepository().setURI(repoUrl).setDirectory(repoDir).setCloneAllBranches(true).call();
		}
	}

	private void init() {
		String os = SystemUtils.OS_NAME;
		String arch = SystemUtils.OS_ARCH;

		if (SystemUtils.IS_OS_LINUX) {

		} else {
			System.err.println("not supported");
			Runtime.getRuntime().exit(4);
		}
		checkUser(debug);

	}

	private void install() throws GitAPIException, FileNotFoundException {
		getRepo();
		buildRepo();

	}

	private void printConfig() {
		System.err.println(config);
	}

	private void test() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
