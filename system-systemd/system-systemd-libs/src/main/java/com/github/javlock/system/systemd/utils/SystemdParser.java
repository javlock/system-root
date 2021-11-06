package com.github.javlock.system.systemd.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.SystemdElement.ELEMENTTYPE;
import com.github.javlock.system.systemd.data.SystemdElement.IPAddressDenyType;
import com.github.javlock.system.systemd.data.SystemdElement.ProtectProcType;
import com.github.javlock.system.systemd.data.SystemdElement.RestrictAddressFamilies;
import com.github.javlock.system.systemd.data.SystemdElement.SystemCallArchitecturesType;
import com.github.javlock.system.systemd.data.sections.Section;
import com.github.javlock.system.systemd.data.sections.Section.SECTIONNAME;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.Path;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.CAPs;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.KillSignalType;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.NotifyAccessType;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.RestartType;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.ServiceSectionType;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.YesNoType;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.data.service.Service;
import com.github.javlock.system.systemd.data.socket.SOCKET;
import com.github.javlock.system.systemd.data.target.Target;
import com.github.javlock.system.systemd.data.timer.Timer;

public class SystemdParser {
	public static interface SystemdParserListener {
		void input(String input) throws Exception;

		void withSection(SECTIONNAME currentSectionName, String input) throws Exception;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger("SystemdParser");

	SECTIONNAME currentSectionName;

	SystemdParserListener listener = new SystemdParserListener() {

		@Override
		public void input(String input) throws Exception {
			if (input.trim().isEmpty()) {
				return;
			}
			if (itComment(input)) {
				return;
			}
			Optional<SECTIONNAME> sectionOptional = isSection(input);
			if (sectionOptional.isPresent()) {
				currentSectionName = sectionOptional.get();
				return;
			}
			try {
				listener.withSection(currentSectionName, input);
			} catch (Exception e) {
				LOGGER.error("currentFile:{} currentSectionName:{} input:{}", currentFile, currentSectionName, input);
				throw new Exception("ВОЗМОЖНА ОШИБКА В SECTIONNAME (нужно добавить новую секцию)", e);
			}
		}

		@Override
		public void withSection(SECTIONNAME sectionName, String input) throws Exception {
			String[] dataArr = null;
			String key = null;
			String value = null;
			try {
				dataArr = input.split("\\=");
				key = dataArr[0];
				value = dataArr[1];
			} catch (Exception e) {
				int index = 0;
				for (String string : dataArr) {
					LOGGER.error(
							"ОШИБКА В sectionName:{} input:{} dataArr:{} dataArr.length:{} index:{} string:{} StackTrace",
							sectionName, input, Arrays.toString(dataArr), dataArr.length, index, string, e);
					index++;
				}
				throw new Exception(e);
			}

			switch (sectionName) {
			case Unit:
				if (element instanceof Service service) {
					Service outService = service;
					UnitSection section = outService.getUnitSection();
					if (key.equalsIgnoreCase("Description")) {
						section.setDescription(value);
						return;
					} else if (key.equalsIgnoreCase("After")) {
						String[] ar = value.split(" ");
						for (String fileName : ar) {
							section.appendAfter(new SystemdElement().fileName(fileName));
						}
						return;
					} else if (key.equalsIgnoreCase("Documentation")) {
						section.setDocumentation(value);
						return;
					} else if (key.equalsIgnoreCase("Wants")) {
						System.out.println("SystemdParser.enclosing_method:Wants()");
						return;
					} else if (key.equalsIgnoreCase("Requires")) {
						section.setRequires(new SystemdElement().fileName(value));
						return;
					}

					else {
						throw new Exception(String.format("неизвестный ключ %s в секции %s", key, currentSectionName));
					}

				} else if (element instanceof Timer timer) {
					Timer timerR = timer;
					System.err.println(timer);
					LOGGER.warn("TODO TIMER#1 ");
				} else if (element instanceof SOCKET socket) {
					SOCKET sockeT = socket;
					LOGGER.warn("TODO SOCKET#1 ");
				} else if (element instanceof Target target) {
					Target targeT = target;
					LOGGER.warn("TODO Target#1 ");
				} else if (element instanceof Path path) {
					Path patH = path;
					LOGGER.warn("TODO Path#1 ");
				}

				else {
					throw new Exception(String.format("неизвестный тип %s", element));
				}
			case Service:
				if (element instanceof Service servicE) {
					Service outService = servicE;
					ServiceSection serviceSection = outService.getServiceSection();
					if (key.equalsIgnoreCase("Type")) {
						serviceSection.setType(ServiceSectionType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("NotifyAccess")) {
						serviceSection.setNotifyAccess(NotifyAccessType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ExecStartPre")) {
						serviceSection.getExecStartPre().add(value);
						return;
					} else if (key.equalsIgnoreCase("ExecStart")) {
						serviceSection.getExecStart().add(value);
						return;
					} else if (key.equalsIgnoreCase("ExecReload")) {
						serviceSection.getExecReload().add(value);
						return;
					} else if (key.equalsIgnoreCase("KillSignal")) {
						serviceSection.setKillSignal(KillSignalType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("TimeoutSec")) {
						serviceSection.setTimeoutSec(Long.parseLong(value));
						return;
					} else if (key.equalsIgnoreCase("Restart")) {
						String correctType = value.replaceAll("-{1,}", "_");
						serviceSection.setRestartType(RestartType.valueOf(correctType));
						return;
					} else if (key.equalsIgnoreCase("WatchdogSec")) {
						serviceSection.setWatchdogSec(value);
						return;
					} else if (key.equalsIgnoreCase("LimitNOFILE")) {
						serviceSection.setLimitNOFILE(Long.parseLong(value));
						return;
					} else if (key.equalsIgnoreCase("PrivateTmp")) {
						serviceSection.setPrivateTmp(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("PrivateDevices")) {
						serviceSection.setPrivateDevices(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectHome")) {
						serviceSection.setProtectHome(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectSystem")) {
						serviceSection.setProtectSystem(value);
						return;
					} else if (key.equalsIgnoreCase("ReadOnlyDirectories")) {
						if (serviceSection.getReadOnlyDirectories() != null) {
							throw new Exception("УЖЕ ЕСТЬ , ПЕРЕПИСАТЬ В КОЛЛЕКЦИЮ!!!");
						}
						serviceSection.setReadOnlyDirectories(value);
						return;
					} else if (key.equalsIgnoreCase("ReadWriteDirectories")) {
						serviceSection.getReadWriteDirectories().add(value);
						return;
					} else if (key.equalsIgnoreCase("NoNewPrivileges")) {
						serviceSection.setNoNewPrivileges(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("CapabilityBoundingSet")) {
						String[] valueArr = value.split(" ");
						for (String cap : valueArr) {
							serviceSection.getCapabilityBoundingSet().add(CAPs.valueOf(cap));
						}
						return;
					} else if (key.equalsIgnoreCase("ExecStop")) {
						serviceSection.getExecStop().add(value);
						return;
					} else if (key.equalsIgnoreCase("PIDFile")) {
						serviceSection.setPIDFile(value);
						return;
					} else if (key.equalsIgnoreCase("OOMScoreAdjust")) {
						serviceSection.setOOMScoreAdjust(Long.parseLong(value));
						return;
					} else if (key.equalsIgnoreCase("BusName")) {
						serviceSection.setBusName(value);
						return;
					} else if (key.equalsIgnoreCase("DeviceAllow")) {
						serviceSection.getDeviceAllow().add(value);
						return;
					} else if (key.equalsIgnoreCase("FileDescriptorStoreMax")) {
						serviceSection.setFileDescriptorStoreMax(Long.parseLong(value));
						return;
					} else if (key.equalsIgnoreCase("IPAddressDeny")) {
						serviceSection.setIAddressDenyType(IPAddressDenyType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("LockPersonality")) {
						serviceSection.setLockPersonality(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("MemoryDenyWriteExecute")) {
						serviceSection.setMemoryDenyWriteExecute(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectProc")) {
						serviceSection.setProtectProcType(ProtectProcType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectClock")) {
						serviceSection.setProtectClock(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectControlGroups")) {
						serviceSection.setProtectControlGroups(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectHostname")) {
						serviceSection.setProtectHostname(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectKernelLogs")) {
						serviceSection.setProtectKernelLogs(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ProtectKernelModules")) {
						serviceSection.setProtectKernelModules(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("ReadWritePaths")) {
						serviceSection.getReadWritePaths().addAll(Arrays.asList(value.split(" ")));
						return;
					} else if (key.equalsIgnoreCase("RestartSec")) {
						serviceSection.setRestartSec(Long.parseLong(value));
						return;
					} else if (key.equalsIgnoreCase("RestrictAddressFamilies")) {
						String[] arr = value.split(" ");
						for (String string : arr) {
							RestrictAddressFamilies families = RestrictAddressFamilies.valueOf(string);
							serviceSection.getRestrictAddressFamiliesType().add(families);
						}
						return;
					} else if (key.equalsIgnoreCase("RestrictRealtime")) {
						serviceSection.setRestrictRealtime(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("RestrictNamespaces")) {
						serviceSection.setRestrictNamespaces(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("RestrictSUIDSGID")) {
						serviceSection.setRestrictSUIDSGID(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("RuntimeDirectory")) {
						serviceSection.getRuntimeDirectory().addAll(Arrays.asList(value.split(" ")));
						return;
					} else if (key.equalsIgnoreCase("RuntimeDirectoryPreserve")) {
						serviceSection.setRuntimeDirectoryPreserve(YesNoType.valueOf(value));
						return;
					} else if (key.equalsIgnoreCase("StateDirectory")) {
						if (serviceSection.getStateDirectory() != null) {
							throw new Exception(String.format("переписать StateDirectory как коллекцию"));
						} else {
							serviceSection.setStateDirectory(value);
						}
						return;
					} else if (key.equalsIgnoreCase("SystemCallArchitectures")) {
						String valuetoUpperCase = value.toUpperCase();
						serviceSection
								.setSystemCallArchitectures(SystemCallArchitecturesType.valueOf(valuetoUpperCase));
						return;
					}

					else {
						throw new Exception(String.format("неизвестный ключ %s в секции %s", key, currentSectionName));
					}

				}

				break;
			case Install:
				if (element instanceof Service servicE) {
					Service outService = servicE;
					InstallSection installSection = outService.getInstallSection();
					if (key.equalsIgnoreCase("WantedBy")) {
						installSection.setWantedBy(new SystemdElement().fileName(value));
						return;
					}
				}
				break;
			case NotConfigured:
			default:
				break;
			}

			// LOGGER.info("withSection:{} {}", sectionName, input);
		}
	};

	SystemdElement element;

	private File currentFile;

	public SystemdParser file(File serviceFile) {
		this.currentFile = serviceFile;
		fileName(currentFile.getName());
		return this;
	}

	public SystemdParser fileName(String name) {
		ELEMENTTYPE type = ServiceUtils.getElementType(name);
		switch (type) {
		case SERVICE: {
			element = new Service();
			break;
		}
		case TARGET: {
			element = new Target();
			break;
		}
		case TIMER: {
			element = new Timer();
			break;
		}
		case SOCKET: {
			element = new SOCKET();
			break;
		}
		case PATH: {
			element = new Path();
			break;
		}
		default:
			throw new IllegalArgumentException(String.format("Unexpected value:%s in section:%s in file:%s  ", type,
					currentSectionName, currentFile));
		}
		return this;
	}

	protected Optional<SECTIONNAME> isSection(String input) {
		for (SECTIONNAME sectionName : Section.SECTIONNAME.values()) {
			String sectionNameF = Section.retNameFor(sectionName);
			if (input.equalsIgnoreCase(sectionNameF)) {
				return Optional.ofNullable(sectionName);
			}
		}
		return Optional.empty();
	}

	protected boolean itComment(String input) {
		return input.trim().startsWith("#");
	}

	public SystemdElement parse(File serviceFile) throws Exception {
		List<String> data = Files.readAllLines(serviceFile.toPath(), StandardCharsets.UTF_8);
		return parse(data);
	}

	public SystemdElement parse(List<String> data) throws Exception {
		for (String string : data) {
			listener.input(string);
		}
		return element;
	}

}
