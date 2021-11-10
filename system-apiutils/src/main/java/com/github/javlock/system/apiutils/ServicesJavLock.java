package com.github.javlock.system.apiutils;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;

public class ServicesJavLock {
	public static class JavlockSystem {
		public static final Logger LOGGER = LoggerFactory.getLogger("ServicesJavLock.JavlockSystem");

	}

	public static final Logger LOGGER = LoggerFactory.getLogger("ServicesJavLock");

	public static File findServicesDir() throws FileNotFoundException {
		if (Paths.systemdDir1.exists()) {
			return Paths.systemdDir1;
		} else if (Paths.systemdDir2.exists()) {
			return Paths.systemdDir2;
		} else {
			throw new FileNotFoundException("Не найден путь до сервисов");
		}
	}
}
