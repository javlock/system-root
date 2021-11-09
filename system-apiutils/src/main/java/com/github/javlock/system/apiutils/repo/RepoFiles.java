package com.github.javlock.system.apiutils.repo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.github.javlock.system.apidata.Paths;

public class RepoFiles {

	public static void findJarWithDeps(ArrayList<File> jars, String repository) throws IOException {
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
				if (name.endsWith(Paths.exeJarSuffix)) {
					jars.add(f);
				}
			}
		}
	}
}
