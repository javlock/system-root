package com.github.javlock.system.kernel.kernelservice;

import java.time.Instant;
import java.util.Optional;

public class ProcessHandler extends Thread {

	interface ProcessListener {

		void process(long nanoTime, ProcessHandle process);

	}

	private static String processDetails(ProcessHandle process) {
		long pid = process.pid();
		Optional<String> user = process.info().user();
		Optional<Instant> time = process.info().startInstant();

		Optional<String> cmd = process.info().commandLine();

		Optional<ProcessHandle> parent = process.parent();
		Optional<Long> parentPid = parent.map(ProcessHandle::pid);

		return String.format("%8d %8s %10s %26s %-40s", pid, text(parentPid), text(user), text(time), text(cmd));
	}

	private static String text(Optional<?> optional) {
		return optional.map(Object::toString).orElse("-");
	}

	boolean active = true;
	ProcessListener processListener = new ProcessListener() {

		@Override
		public void process(long nanoTime, ProcessHandle process) {
			Optional<String> userOptional = process.info().user();
			if (userOptional.isPresent()) {
				String user = userOptional.get();
				if (user.equalsIgnoreCase("tor")) {
					System.err.println(processDetails(process));
				}
			}
		}
	};

	@Override
	public void run() {
		setName("ProcessHandler");
		do {
			/*
			 * ProcessHandle.allProcesses().parallel().forEach(process -> {
			 * processListener.process(System.nanoTime(), process); });
			 */
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (active);
	}
}
