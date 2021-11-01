package com.github.javlock.system.installer.init;

import java.io.File;
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
		GUI, GUILESS
	}

	public static SecureRandom random = new SecureRandom();

	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMERIC = "0123456789";
	public static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

	public static String generatePassword(int len, String dic) {
		String result = "";
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
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

	private void buildRepo() {

	}

	private void checkUser(boolean deb) {
		String user = SystemUtils.USER_NAME;
		System.err.println("Пользователь " + user);
		if (user.equals("root")) {
		} else {
			if (!deb) {
				Runtime.getRuntime().exit(5);
			} else {
				System.err.println("не вышли тк включена отладка");
			}
		}
	}

	private void getRepo() throws InvalidRemoteException, TransportException, GitAPIException {
		File repoDir = new File("/opt/javlock-system");
		if (!repoDir.exists()) {
			String repoUrl = "https://github.com/javlock/system-root";
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

	private void install() throws InvalidRemoteException, TransportException, GitAPIException {
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
