package com.github.javlock.system.apiutils.repo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;

public class RepoFiles {

	private static final Logger LOGGER = LoggerFactory.getLogger("RepoFiles");

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

	static void movedExecsJarsToPersDir() throws IOException {
		ArrayList<File> jars = new ArrayList<>();
		RepoFiles.findJarWithDeps(jars, Paths.repoDir.getAbsolutePath());
		File newDir = Paths.repoDirJars;
		if (!newDir.exists()) {
			newDir.mkdirs();
		}

		String updaterPREF = "system-updater";
		File updaterNewFile = new File(newDir, updaterPREF + ".jar");

		String kernelPREF = "system-kernel";
		File kernelNewFile = new File(newDir, kernelPREF + ".jar");

		for (File file : jars) {
			LOGGER.info("Найден исполняемый файл:{}", file);
			String fileName = file.getName();
			String fileNameLC = fileName.toLowerCase();

			// files
			if (fileNameLC.startsWith(updaterPREF.toLowerCase()) && fileNameLC.endsWith(Paths.exeJarSuffix)) {
				Files.copy(file.toPath(), updaterNewFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			if (fileNameLC.startsWith(kernelPREF.toLowerCase()) && fileNameLC.endsWith(Paths.exeJarSuffix)) {
				Files.copy(file.toPath(), kernelNewFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}

			// files
		}
	}
}
