package com.github.javlock.system.apidata;

import java.io.File;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "DMI_HARDCODED_ABSOLUTE_FILENAME")
public class Paths {
	public static final File repoDir = new File(new File("/", "opt"), "javlock-system");
	public static final String REPOURL = "https://github.com/javlock/system-root";

	public static final File repoDirJars = new File(repoDir, "exe-jars");

	public static final File systemdDir1 = new File("/", "usr/lib/systemd/system/");
	public static final File systemdDir2 = new File("/", "etc/systemd/system/");

	public static final String EXEJARSUFFIX = "-jar-with-dependencies.jar";

	public static final String BASH = "bash";
	private static final String TMP = "/tmp";
	public static final File TMPDIR = new File(TMP);

}
