package com.github.javlock.system.installer.config;

import static com.github.javlock.system.installer.init.InstallerInit.ALPHA;
import static com.github.javlock.system.installer.init.InstallerInit.ALPHA_CAPS;
import static com.github.javlock.system.installer.init.InstallerInit.SPECIAL_CHARS;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.javlock.system.installer.init.InstallerInit;

import lombok.Getter;
import lombok.Setter;

public class InstallerConfig {

	private @Getter @Setter String dbHost = "127.0.0.1";
	private @Getter @Setter int dbPort = 5432;

	private @Getter @Setter String dbName = "javLockSystem";
	private @Getter @Setter String dbUserName = "javLock";
	private @Getter @Setter String dbUserPassword = InstallerInit.generatePassword(gen(30, 60),
			ALPHA_CAPS + ALPHA + SPECIAL_CHARS);

	private int gen(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
