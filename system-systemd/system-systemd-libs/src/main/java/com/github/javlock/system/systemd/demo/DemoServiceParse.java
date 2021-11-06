package com.github.javlock.system.systemd.demo;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apiutils.ServicesJavLock;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.utils.ServiceUtils;

public class DemoServiceParse {
	interface DemoServiceParseListener {
		void appendDir(File dir) throws Exception;

		void appendFile(File file) throws Exception;
	}

	static DemoServiceParseListener listener = new DemoServiceParseListener() {

		@Override
		public void appendDir(File dir) throws Exception {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					listener.appendFile(file);
				} else if (file.isDirectory()) {
					listener.appendDir(file);
				}
			}

		}

		@Override
		public void appendFile(File file) throws Exception {
			SystemdElement element2 = ServiceUtils.parseFile(file);
			LoggerFactory.getLogger("DemoServiceParse:parseFile").info("\n{}", element2.toServiceFile());
		}

	};

	private static final Logger LOGGER = LoggerFactory.getLogger("DemoServiceParse");

	public static void main(String[] args) {
		try {
			File serviceDir = ServicesJavLock.findServicesDir();
			listener.appendDir(serviceDir);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
