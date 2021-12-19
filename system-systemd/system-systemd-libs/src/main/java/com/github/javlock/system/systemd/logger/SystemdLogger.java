package com.github.javlock.system.systemd.logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javlock.ostools.executor.ExecutorMaster;
import com.github.javlock.ostools.executor.ExecutorMasterOutputListener;
import com.github.javlock.system.JOB_TYPE;
import com.github.javlock.system.auditd._AUDIT_TYPE_NAME;

public class SystemdLogger extends Thread {
	public static interface SystemdLoggerInterface {

		void appendInput(String line) throws Exception;

		void appendValue(SystemdLogValue map);

	}

	public static interface SystemdLogInterface {
		void receiveLog(SystemdLogValue logValue);
	}

	private static final String STRING = "[{}] [{}] [{}]";

	private static final Logger LOGGER = LoggerFactory.getLogger("SystemdLogger");

	public static void main(String[] args) throws Exception {
		SystemdLogger systemdLogger = new SystemdLogger();
		systemdLogger.start();
		systemdLogger.readOld();
	}

	private SystemdLogInterface logInterface = logValue -> {
		for (SystemdLogInterface logInterface : this.listeners) {
			logInterface.receiveLog(logValue);
		}
	};

	CopyOnWriteArrayList<SystemdLogInterface> listeners = new CopyOnWriteArrayList<>();

	ObjectMapper mapper = new ObjectMapper();
	CopyOnWriteArrayList<String> error = new CopyOnWriteArrayList<>();

	SystemdLoggerInterface loggerInterface = new SystemdLoggerInterface() {

		@Override
		public void appendInput(String line) throws Exception {

			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
			};
			try {
				Map<String, Object> mapping = mapper.readValue(line, typeRef);

				SystemdLogValue value = new SystemdLogValue();

				for (Entry<String, Object> entry : mapping.entrySet()) {
					String key = entry.getKey();
					Object val = entry.getValue();
					try {
						SystemdFields.class.getDeclaredField(key);
					} catch (NoSuchFieldException | SecurityException e) {
						String errorKey = e.getMessage().replaceAll("\"", "");
						String newCode = String.format("String %s = \"%s\" ;", errorKey, errorKey);

						System.err.println(newCode);
					}
					try {
						if (val == null) {
							continue;
						}
						String valAsString = val.toString();

						Field field = value.getClass().getDeclaredField(key);
						Class<?> type = field.getType();
						if (type.equals(String.class)) {
							field.set(value, valAsString);
						} else if (type.equals(Long.class)) {
							field.set(value, Long.parseLong(valAsString));
						} else if (type.equals(Integer.class)) {
							field.set(value, Integer.parseInt(valAsString));
						} else if (type.equals(Double.class)) {
							field.set(value, Double.parseDouble(valAsString));
						} else if (type.equals(CopyOnWriteArrayList.class)) {
							CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(); // FIXME
							String[] array = valAsString.split("\n");
							list.addAll(Arrays.asList(array));
							field.set(value, list);
						} else if (type.equals(ArrayList.class)) {
							ArrayList<String> list = new ArrayList<>(); // FIXME
							String[] array = valAsString.split(", ");
							list.addAll(Arrays.asList(array));
							field.set(value, list);
						} else if (type.equals(HashMap.class)) {
							HashMap<String, String> map = new HashMap<>();// FIXME
							String[] lines = valAsString.split("\n");
							for (String row : lines) {
								String[] r = row.split("=");
								String k = r[0];
								String v = null;
								if (r.length == 2) {
									v = r[1];
								}
								map.put(k, v);
							}
							field.set(value, map);
						} else {
							if (type.getSuperclass().equals(Enum.class)) {// ENUMs
								if (type.equals(_AUDIT_TYPE_NAME.class)) {
									field.set(value, _AUDIT_TYPE_NAME.valueOf(valAsString));
								} else if (type.equals(JOB_TYPE.class)) {
									field.set(value, JOB_TYPE.valueOf(valAsString));
								} else {
									LOGGER.warn(STRING, type, key, valAsString);
								}
							} else {
								LOGGER.warn(STRING, type, key, valAsString);
							}
						}

					} catch (RuntimeException e) {
						throw e;
					} catch (NoSuchFieldException e) {
						System.err.println(String.format("Integer %s  ;", key));
					} catch (Exception e) {
						// e.printStackTrace();

						// error.addIfAbsent(e.getMessage() + " " + val);

						/*
						 * if (!key.equalsIgnoreCase("COREDUMP_PROC_MAPS") && val != null &&
						 * val.toString().length() > 500) { val = "size.>>>500"; }
						 *
						 */
						LOGGER.error(STRING, e.getClass(), key, val);
						throw e;
					}
				}
				appendValue(value);
			} catch (JsonProcessingException | SecurityException e1) {
				e1.printStackTrace();
			}

		}

		@Override
		public void appendValue(SystemdLogValue value) {
			logInterface.receiveLog(value);
		}
	};

	ExecutorMasterOutputListener outputListener = new ExecutorMasterOutputListener() {
		@Override
		public void appendInput(String line) {
			// IGNORE
		}

		@Override
		public void appendOutput(String line) throws Exception {
			loggerInterface.appendInput(line);
		}

		@Override
		public void startedProcess(Long pid) {
			// TODO Auto-generated method stub

		}
	};

	private void readOld() throws Exception {
		ExecutorMaster executorMasterOld = new ExecutorMaster();
		executorMasterOld.setOutputListener(outputListener);
		executorMasterOld.parrentCommand("bash").command("journalctl  -o  json");
		executorMasterOld.call();

		Collections.sort(error);
		for (String string : error) {
			LOGGER.error(string);
		}
	}

	@Override
	public void run() {
		try {
			Thread.currentThread().setName("SystemdLogger");
			ExecutorMaster executorMaster = new ExecutorMaster();
			executorMaster.setOutputListener(outputListener);
			executorMaster.parrentCommand("bash").command("journalctl -f   -o  json");
			executorMaster.call();
		} catch (InterruptedException e) {
			LOGGER.error("Interrupted!", e);
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
