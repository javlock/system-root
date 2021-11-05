package com.github.javlock.system.apidata;

import java.io.File;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "DMI_HARDCODED_ABSOLUTE_FILENAME")
public class Paths {
	public static final File repoDir = new File(new File("/", "opt"), "javlock-system");
	public static final String repoUrl = "https://github.com/javlock/system-root";

	public static final File systemdDir1 = new File("/", "usr/lib/systemd/system/");
	public static final File systemdDir2 = new File("/", "etc/systemd/system/");

	public static final String exeJarSuffix = "-jar-with-dependencies.jar";
}
