package com.github.javlock.system.installer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExecutorMaster {
	ProcessBuilder processBuilder = new ProcessBuilder();

	private String parentProg;
	private ExecutorMasterOutputListener outputListener;

	CopyOnWriteArrayList<String> progs = new CopyOnWriteArrayList<>();

	public int call() throws IOException, InterruptedException {
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		Thread commandAppender = new Thread((Runnable) () -> {
			OutputStream os = process.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);

			for (String string : progs) {
				try {
					osw.append(string);
					if (outputListener != null) {
						outputListener.appendInput(string);
					}
					osw.append('\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				osw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}, parentProg);
		commandAppender.start();
		String line;
		while ((line = reader.readLine()) != null) {
			if (outputListener != null) {
				outputListener.appendOutput(line);
			}
		}
		int exitCode = process.waitFor();
		commandAppender.join();
		return exitCode;
	}

	public ExecutorMaster command(String string) {
		progs.add(string);
		return this;
	}

	public ExecutorMaster dir(File repoDir) {
		processBuilder.directory(repoDir);
		return this;
	}

	public ExecutorMaster parrentCommand(String cmd) {
		parentProg = cmd;
		processBuilder.command(parentProg);
		return this;
	}

	public ExecutorMaster setOutputListener(ExecutorMasterOutputListener listener) {
		this.outputListener = listener;
		return this;
	}

}
