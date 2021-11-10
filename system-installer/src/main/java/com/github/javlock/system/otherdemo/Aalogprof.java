package com.github.javlock.system.otherdemo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apiutils.ExecutorMaster;
import com.github.javlock.system.apiutils.ExecutorMasterOutputListener;

public class Aalogprof extends Thread {
	public static final Logger LOGGER = LoggerFactory.getLogger("Aalogprof");

	static AalogprofResult res = new AalogprofResult();

	public static void main(String[] args) {
		Aalogprof aalogprof = new Aalogprof();
		aalogprof.start();

		do {

			try {
				ExecutorMaster master = new ExecutorMaster();

				master.parrentCommand("bash").command("sudo -u root bash").command("aa-logprof").dir(new File("/tmp"))
						.setOutputListener(new ExecutorMasterOutputListener() {

							@Override
							public void appendInput(String line) {
								// no output
							}

							@Override
							public void appendOutput(String line) {
								LOGGER.info(line);
								try {
									res.parseLine(line);
								} catch (Exception e) {
									LOGGER.error(line);
									throw e;
								}
							}
						});

				int exitStatus = master.call();

				if (!res.check()) {
					res.clear();
					Thread.sleep(30);
					continue;
				}

				StringBuilder dataForAppend = new StringBuilder();
				dataForAppend.append("  #AFTER").append('\n');
				dataForAppend.append(res.toRuleString());

				File ruleFile = res.getProfileFile();

				CopyOnWriteArrayList<String> newData = new CopyOnWriteArrayList<>();

				List<String> currentData = Files.readAllLines(ruleFile.toPath(), StandardCharsets.UTF_8);
				for (String string : currentData) {
					if (string.trim().equalsIgnoreCase("#AFTER")) {
						newData.add(dataForAppend.toString());
					} else {
						newData.add(string);
					}
				}

				StringBuilder result = new StringBuilder();
				for (String string : newData) {
					result.append(string).append('\n');
				}

				Files.writeString(res.getProfileFile().toPath(), result, StandardOpenOption.TRUNCATE_EXISTING);

				ExecutorMaster reloader = new ExecutorMaster();

				reloader.parrentCommand("bash").command("sudo -u root bash")
						.command("apparmor_parser -r " + res.getProfileFile().getAbsolutePath()).dir(new File("/tmp"));
				reloader.setOutputListener(new ExecutorMasterOutputListener() {

					@Override
					public void appendInput(String line) {
						// ignore
					}

					@Override
					public void appendOutput(String line) {
						// ignore
					}
				});
				int exitStatusReloader = reloader.call();
				LOGGER.info("{}", res);

				res.clear();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);

	}

	@Override
	public void run() {

	}

}
