package com.github.javlock.system.apiutils.os;

import java.io.File;
import java.io.FileNotFoundException;

public class OsUtils {
	public static String findProgInSys(String name) throws FileNotFoundException {
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

	public static String getSystemShell() throws FileNotFoundException {
		String shell = null;
		try {
			shell = OsUtils.findProgInSys("bash");
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (shell == null) {
			shell = OsUtils.findProgInSys("sh");
		}
		return shell;
	}

}
