package com.github.javlock.system.installer.init;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.naming.ConfigurationException;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.DataSets;
import com.github.javlock.system.apidata.Paths;
import com.github.javlock.system.apiutils.repo.RepoUtils;
import com.github.javlock.system.apiutils.repo.git.GitHelper;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printHelp() {
		StringBuilder builder = new StringBuilder();
		builder.append('\n');
		builder.append("--version=VALUE for start install value of " + Arrays.toString(DataSets.VERSIONTYPE.values())
				+ " version").append('\n');
		builder.append("--debug for start install with debug messages").append('\n');
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

		String vs = "--version=".toUpperCase();

		for (String arg : args) {
			String argUC = arg.toUpperCase();

			if (argUC.startsWith(vs)) {
				String value = argUC.split("=")[1].toUpperCase();
				init.config.setVersion(DataSets.VERSIONTYPE.valueOf(value));
			}

			if (arg.equalsIgnoreCase("--ok")) {
				init.config.setPrepare(true);
			}

			if (arg.equalsIgnoreCase("--debug")) {
				init.debug = true;
			}
		}
		LOGGER.info("\n{}", init.config);
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

	private void exit() {

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

	private void install() throws Exception {
		String branch = config.getVersion().toString().toLowerCase();
		GitHelper.getRepo(Paths.REPOURL, Paths.repoDir, branch);
		RepoUtils.downloadAndBuild();
		LOGGER.info("Проверка git репозитория успешно завершена");
	}

	private void test() {
		// TODO написать тесты для отладки сервисов после установки
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
