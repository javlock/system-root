package com.github.javlock.system.systemd.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.NotParsedException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.SystemdElement.CAPs;
import com.github.javlock.system.systemd.data.SystemdElement.ConditionSecurityType;
import com.github.javlock.system.systemd.data.SystemdElement.DefaultDependenciesType;
import com.github.javlock.system.systemd.data.SystemdElement.DevicePolicyType;
import com.github.javlock.system.systemd.data.SystemdElement.ELEMENTTYPE;
import com.github.javlock.system.systemd.data.SystemdElement.IOSchedulingClassType;
import com.github.javlock.system.systemd.data.SystemdElement.JobTimeoutActionType;
import com.github.javlock.system.systemd.data.SystemdElement.KeyringModeType;
import com.github.javlock.system.systemd.data.SystemdElement.KillModeType;
import com.github.javlock.system.systemd.data.SystemdElement.KillSignalType;
import com.github.javlock.system.systemd.data.SystemdElement.NotifyAccessType;
import com.github.javlock.system.systemd.data.SystemdElement.OnFailureJobModeType;
import com.github.javlock.system.systemd.data.SystemdElement.ProtectProcType;
import com.github.javlock.system.systemd.data.SystemdElement.RestartKillSignalType;
import com.github.javlock.system.systemd.data.SystemdElement.RestartType;
import com.github.javlock.system.systemd.data.SystemdElement.RestrictAddressFamiliesType;
import com.github.javlock.system.systemd.data.SystemdElement.SECTIONNAME;
import com.github.javlock.system.systemd.data.SystemdElement.SuccessActionType;
import com.github.javlock.system.systemd.data.SystemdElement.Type;
import com.github.javlock.system.systemd.data.automount.AUTOMOUNT;
import com.github.javlock.system.systemd.data.conf.CONF;
import com.github.javlock.system.systemd.data.mount.MOUNT;
import com.github.javlock.system.systemd.data.path.PATH;
import com.github.javlock.system.systemd.data.sections.Section;
import com.github.javlock.system.systemd.data.service.Service;
import com.github.javlock.system.systemd.data.socket.SOCKET;
import com.github.javlock.system.systemd.data.target.Target;
import com.github.javlock.system.systemd.data.timer.Timer;
import com.github.javlock.system.systemd.demo.DemoServiceParse;
import com.github.javlock.system.systemd.utils.slice.SLICE;

public class SystemdParser {
	public static interface SystemdParserListener {
		void errorStream(String msg);

		void input(String input) throws Exception;

		void withSection(Section section, String key, String value) throws Exception;

		void withSectionName(SECTIONNAME currentSectionName, String input) throws Exception;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger("SystemdParser");

	boolean logToDo;

	SECTIONNAME currentSectionName;

	SystemdParserListener listener = new SystemdParserListener() {

		@Override
		public void errorStream(String msg) {
			System.out.println("SystemdParser.errorStream()");
			try {
				if (!DemoServiceParse.errorMessages.contains(msg)) {
					DemoServiceParse.errorMessages.add(msg);
				}
				throw new Exception(msg);
			} catch (Exception e) {
				LOGGER.info("{}", msg, e);
				if (!DemoServiceParse.errorMessages.contains(msg)) {
					DemoServiceParse.errorMessages.add(msg);
				}
			}
		}

		@Override
		public void input(String input) throws NotParsedException {
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
			} else {
				String trimOnput = input.trim();
				if (trimOnput.startsWith("[") && trimOnput.endsWith("]")) {
					throw new NotParsedException(String
							.format("Не сменена секция %s (возможно её нет в SystemdElement.SECTIONNAME)", input));
				}
			}
			try {
				listener.withSectionName(currentSectionName, input);
			} catch (Exception e) {
				String msg = String.format("currentFile:[%s] currentSectionName:[%s] input:[%s]", currentFile,
						currentSectionName, input);
				throw new NotParsedException(msg, e);
			}
		}

		@Override
		public void withSection(Section section, String key, String value) throws Exception {
			if (!checkWithSection(currentFile, section, key, value)) {
				return;
			}
			parseWithSection(section, key, value);
		}

		@Override
		public void withSectionName(SECTIONNAME sectionName, String input) throws Exception {
			String[] dataArr = null;
			String key = null;
			String value = null;
			try {
				dataArr = input.split("\\=");
				key = dataArr[0];
				value = dataArr[1];
			} catch (Exception e) {// ignore
			}
			if (!checkWithSectionName(currentFile, sectionName, key, value)) {
				return;
			}

			Section section = Section.getSectionFor(currentFile, element, sectionName);
			listener.withSection(section, key, value);
		}
	};

	SystemdElement element;

	private File currentFile;

	protected boolean checkWithSection(File file, Section section, String key, String value)
			throws NullPointerException, IllegalArgumentException {
		if (key == null) {
			LOGGER.error("CHECK=checkWithSection key==null For sectionName:[{}] with VALUE:[{}] for File:[{}]", section,
					key, file);
			return false;
		}
		if (key.isEmpty()) {
			LOGGER.error("CHECK=checkWithSection key.isEmpty() for File:[{}]", file);
			return false;
		}
		if (value == null) {
			LOGGER.error("CHECK=checkWithSection value==null For sectionName:[{}] with KEY:[{}] for File:[{}]",
					Section.retNameFor(section.getName()), key, file);
			return false;
		}
		if (value.isEmpty()) {
			LOGGER.error("CHECK=checkWithSection value.isEmpty() for File:[{}]", file);
			return false;
		}
		return true;

	}

	protected boolean checkWithSectionName(File file, SECTIONNAME sectionName, String key, String value) {
		if (key == null) {
			LOGGER.error("CHECK=checkWithSectionName key==null For sectionName:[{}] with VALUE:[{}] for File:[{}]",
					sectionName, key, file);
			return false;
		}
		if (key.isEmpty()) {
			LOGGER.error("CHECK=checkWithSectionName key.isEmpty() for File:[{}]", file);
			return false;
		}
		if (value == null) {
			LOGGER.error("CHECK=checkWithSectionName value==null For sectionName:[{}] with VALUE:[{}] for File:[{}]",
					sectionName, key, file);
			return false;
		}
		if (value.isEmpty()) {
			LOGGER.error("CHECK=checkWithSectionName value.isEmpty() for File:[{}]", file);
			return false;
		}
		return true;
	}

	public SystemdParser file(File serviceFile) {
		this.currentFile = serviceFile;
		fileName(currentFile.getName());
		return this;
	}

	/**
	 *
	 * @param name FileName
	 *
	 * @implNote Caused by: java.lang.NullPointerException: Cannot invoke
	 *           "com.github.javlock.system.systemd.data.SystemdElement.getUnitSection()"
	 *           because "element" is null
	 * @return
	 */
	public SystemdParser fileName(String name) {
		ELEMENTTYPE type = ServiceUtils.getElementType(name);

		switch (type) {
		case SERVICE: {
			element = new Service();
			break;
		}
		case TARGET:
			element = new Target();
			break;
		case TIMER:
			element = new Timer();
			break;
		case SOCKET:
			element = new SOCKET();
			break;
		case PATH:
			element = new PATH();
			break;
		case MOUNT:
			element = new MOUNT();
			break;
		case AUTOMOUNT:
			element = new AUTOMOUNT();
			break;
		case CONF:
			element = new CONF();
			break;
		case SLICE:
			element = new SLICE();
			break;
		default:
			String errorMsg = String.format(
					"Обработай в public SystemdParser fileName(String name) value:%s in section:%s in file:%s  ", type,
					currentSectionName, currentFile);
			listener.errorStream(errorMsg);
		}
		return this;
	}

	protected Optional<SECTIONNAME> isSection(String input) {
		for (SECTIONNAME sectionName : SECTIONNAME.values()) {
			String sectionNameF = Section.retNameFor(sectionName);
			if (input.equalsIgnoreCase(sectionNameF)) {
				return Optional.ofNullable(sectionName);
			}
		}
		return Optional.empty();
	}

	protected boolean itComment(String input) {
		String trim = input.trim();
		if (trim.startsWith(";")) {
			return true;
		}
		return trim.startsWith("#");
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

	protected void parseWithSection(Section section, String key, String value) throws Exception {
		if (key.equalsIgnoreCase("Description")) {
			section.setDescription(value);
			return;
		}
		if (key.equalsIgnoreCase("Type")) {
			section.setTypeVar(Type.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("ExecStart")) {
			section.getExecStart().add(value);
			return;
		}
		if (key.equalsIgnoreCase("ExecStop")) {
			section.getExecStop().add(value);
			return;
		}
		if (key.equalsIgnoreCase("ExecReload")) {
			section.getExecReload().add(value);
			return;
		}
		if (key.equalsIgnoreCase("Restart")) {
			section.setRestart(RestartType.valueOf(value.replace("-", "_")));
			return;
		}
		if (key.equalsIgnoreCase("PIDFile")) {
			if (section.getPIDFile() != null) {
				throw new NotParsedException("переделать PIDFile в коллекцию");
			}
			section.setPIDFile(value);
			return;
		}
		if (key.equalsIgnoreCase("WantedBy")) {
			String[] valueArr = value.split(" ");
			for (String fileName : valueArr) {
				section.getWantedBy().add(new SystemdElement().fileName(fileName));
			}
			return;
		}
		if (key.equalsIgnoreCase("Documentation")) {
			String[] valueArr = value.split(" ");
			for (String doc : valueArr) {
				section.getDocumentation().add(doc);
			}
			return;
		}
		if (key.equalsIgnoreCase("OnCalendar")) {
			section.setOnCalendar(value);
			return;
		}
		if (key.equalsIgnoreCase("AccuracySec")) {
			section.setAccuracySec(value);
			return;
		}
		if (key.equalsIgnoreCase("Persistent")) {
			section.setPersistent(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("RandomizedDelaySec")) {
			section.setRandomizedDelaySec(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathExists")) {
			section.getConditionPathExists().add(value);
			return;
		}
		if (key.equalsIgnoreCase("OnBootSec")) {
			if (section.getOnBootSec() != null) {
				throw new NotParsedException("переписать OnBootSec в коллекцию");
			}
			section.setOnBootSec(value);
			return;
		}
		if (key.equalsIgnoreCase("OnUnitActiveSec")) {
			if (section.getOnUnitActiveSec() != null) {
				throw new NotParsedException("переписать OnUnitActiveSec в коллекцию");
			}
			section.setOnUnitActiveSec(value);
			return;
		}
		if (key.equalsIgnoreCase("ListenStream")) {
			section.getListenStream().addIfAbsent(value);
			return;
		}
		if (key.equalsIgnoreCase("DefaultDependencies")) {
			section.setDefaultDependencies(DefaultDependenciesType.valueOf(value));
			return;
		}

		if (key.equalsIgnoreCase("ListenFIFO")) {
			section.getListenFIFO().add(value);
			return;
		}

		if (key.equalsIgnoreCase("SocketMode")) {
			if (section.getSocketMode() != null) {
				throw new NotParsedException("переписать SocketMode в коллекцию");
			}
			section.setSocketMode(value);
			return;
		}
		if (key.equalsIgnoreCase("RemoveOnStop")) {
			section.setRemoveOnStop(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Before")) {
			section.getBefore().add(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("Conflicts")) {
			section.getConflicts().add(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("ListenSequentialPacket")) {
			if (section.getListenSequentialPacket() != null) {
				throw new NotParsedException("переписать ListenSequentialPacket в коллекцию");
			}
			section.setListenSequentialPacket(value);
			return;
		}
		if (key.equalsIgnoreCase("Accept")) {
			section.setAccept(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("MaxConnections")) {
			section.setMaxConnections(Long.parseLong(value));
			return;
		}
		if (key.equalsIgnoreCase("ConditionSecurity")) {
			section.setConditionSecurity(ConditionSecurityType.valueOf(value.replace("-", "_")));
			return;
		}
		if (key.equalsIgnoreCase("ConditionCapability")) {
			section.getConditionCapability().add(CAPs.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("Service")) {
			if (section.getService() != null) {
				throw new NotParsedException("переписать Service= в Коллекцию");
			}
			section.setService(new SystemdElement().fileName(value));
			return;
		}

		if (key.equalsIgnoreCase("ReceiveBuffer")) {
			if (section.getReceiveBuffer() != null) {
				throw new NotParsedException("переписать ReceiveBuffer в Коллекцию");
			}
			section.setReceiveBuffer(value);
			return;
		}
		if (key.equalsIgnoreCase("ListenNetlink")) {
			if (section.getListenNetlink() != null) {
				throw new NotParsedException("переписать ListenNetlink в Коллекцию");
			}
			section.setListenNetlink(value);
			return;
		}
		if (key.equalsIgnoreCase("PassCredentials")) {
			section.setPassCredentials(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("IgnoreOnIsolate")) {
			section.setIgnoreOnIsolate(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ListenDatagram")) {
			section.getListenDatagram().add(value);
			return;
		}
		if (key.equalsIgnoreCase("PassSecurity")) {
			section.setPassSecurity(Boolean.parseBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Symlinks")) {
			String[] arr = value.split(" ");
			section.getSymlinks().addAll(Arrays.asList(arr));
			return;
		}
		if (key.equalsIgnoreCase("Timestamping")) {
			if (section.getTimestamping() != null) {
				throw new NotParsedException("переписать Timestamping в истинный тип");
			}
			section.setTimestamping(value);
			return;
		}
		if (key.equalsIgnoreCase("SendBuffer")) {
			if (section.getSendBuffer() != null) {
				throw new NotParsedException("переписать SendBuffer в коллекцию");
			}
			section.setSendBuffer(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathIsReadWrite")) {
			if (section.getConditionPathIsReadWrite() != null) {
				throw new NotParsedException("переписать ConditionPathIsReadWrite в коллекцию");
			}
			section.setConditionPathIsReadWrite(value);
			return;
		}
		if (key.equalsIgnoreCase("Requires")) {
			section.getRequires().add(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("OOMScoreAdjust")) {
			if (section.getOOMScoreAdjust() != null) {
				throw new NotParsedException("переписать OOMScoreAdjust в коллекцию");
			}
			section.setOOMScoreAdjust(value);
			return;
		}
		if (key.equalsIgnoreCase("DirectoryNotEmpty")) {
			if (section.getDirectoryNotEmpty() != null) {
				throw new NotParsedException("переписать DirectoryNotEmpty в коллекцию");
			}
			section.setDirectoryNotEmpty(value);
			return;
		}
		if (key.equalsIgnoreCase("MakeDirectory")) {
			section.setMakeDirectory(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Wants")) {
			String[] valueArr = value.split(" ");
			for (String fileName : valueArr) {
				section.getWants().add(new SystemdElement().fileName(fileName));
			}
			return;
		}
		if (key.equalsIgnoreCase("After")) {
			String[] valueArr = value.split(" ");
			for (String fileName : valueArr) {
				section.getAfter().add(new SystemdElement().fileName(fileName));
			}
			return;
		}

		if (key.equalsIgnoreCase("BusName")) {
			if (section.getBusName() != null) {
				throw new NotParsedException("переписать BusName в коллекцию");
			}
			section.setBusName(value);
			return;
		}

		if (key.equalsIgnoreCase("CapabilityBoundingSet")) {
			String[] valueArr = value.split(" ");
			for (String cap : valueArr) {
				section.getCapabilityBoundingSet().add(CAPs.valueOf(cap.replace("~", "TILDE_")));
			}
			return;
		}
		if (key.equalsIgnoreCase("DeviceAllow")) {
			section.getDeviceAllow().add(value);
			return;
		}
		if (key.equalsIgnoreCase("FileDescriptorStoreMax")) {
			section.setFileDescriptorStoreMax(Long.parseLong(value));
			return;
		}
		if (key.equalsIgnoreCase("IPAddressDeny")) {
			if (section.getIPAddressDeny() != null) {
				throw new NotParsedException("переписать IPAddressDeny в коллекцию");
			}
			section.setIPAddressDeny(value);
			return;
		}
		if (key.equalsIgnoreCase("LockPersonality")) {
			section.setLockPersonality(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("MemoryDenyWriteExecute")) {
			section.setMemoryDenyWriteExecute(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("NoNewPrivileges")) {
			section.setNoNewPrivileges(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("PrivateTmp")) {
			section.setPrivateTmp(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectClock")) {
			section.setProtectClock(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectControlGroups")) {
			section.setProtectControlGroups(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectHome")) {
			section.setProtectHome(value);
			return;
		}
		if (key.equalsIgnoreCase("ProtectHostname")) {
			section.setProtectHostname(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectKernelLogs")) {
			section.setProtectKernelLogs(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectKernelModules")) {
			section.setProtectKernelModules(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectProc")) {
			section.setProtectProc(ProtectProcType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("RestartSec")) {
			section.setRestartSec(value);
			return;
		}
		if (key.equalsIgnoreCase("ProtectSystem")) {
			if (section.getProtectSystem() != null) {
				throw new IllegalArgumentException("переписать ProtectSystem в коллекцию");
			}
			section.setProtectSystem(value);
			return;
		}
		if (key.equalsIgnoreCase("ReadWritePaths")) {
			String[] valueArr = value.split(" ");
			if (valueArr.length == 0) {
				section.getReadWritePaths().add(value);
			} else {
				for (String string : valueArr) {
					section.getReadWritePaths().add(string);
				}
			}
			return;
		}

		if (key.equalsIgnoreCase("RestrictAddressFamilies")) {
			String[] valueArr = value.split(" ");
			for (String string : valueArr) {
				section.getRestrictAddressFamilies().add(RestrictAddressFamiliesType.valueOf(string));
			}
			return;
		}
		if (key.equalsIgnoreCase("RestrictNamespaces")) {
			if (section.getRestrictNamespaces() != null) {
				throw new IllegalArgumentException("переписать RestrictNamespaces в коллекцию");
			}
			section.setRestrictNamespaces(value);
			return;
		}

		if (key.equalsIgnoreCase("RestrictRealtime")) {
			section.setRestrictRealtime(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("RestrictSUIDSGID")) {
			section.setRestrictSUIDSGID(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("RuntimeDirectoryPreserve")) {
			section.setRuntimeDirectoryPreserve(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("StateDirectory")) {
			if (section.getStateDirectory() != null) {
				throw new IllegalArgumentException("переписать StateDirectory в коллекцию");
			}
			section.setStateDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("RuntimeDirectory")) {
			if (section.getRuntimeDirectory() != null) {
				throw new IllegalArgumentException("переписать RuntimeDirectory в коллекцию");
			}
			section.setRuntimeDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("SystemCallArchitectures")) {
			if (section.getSystemCallArchitectures() != null) {
				throw new IllegalArgumentException("переписать SystemCallArchitectures в коллекцию");
			}
			section.setSystemCallArchitectures(value);
			return;
		}
		if (key.equalsIgnoreCase("SystemCallErrorNumber")) {
			if (section.getSystemCallErrorNumber() != null) {
				throw new IllegalArgumentException("переписать SystemCallErrorNumber в коллекцию");
			}
			section.setSystemCallErrorNumber(value);
			return;
		}
		if (key.equalsIgnoreCase("SystemCallFilter")) {
			section.getSystemCallFilter().add(value);
			return;
		}
		if (key.equalsIgnoreCase("WatchdogSec")) {
			if (section.getWatchdogSec() != null) {
				throw new IllegalArgumentException("переписать WatchdogSec в коллекцию");
			}
			section.setWatchdogSec(value);
			return;
		}
		if (key.equalsIgnoreCase("LimitNOFILE")) {
			if (section.getLimitNOFILE() != null) {
				throw new IllegalArgumentException("переписать LimitNOFILE в коллекцию");
			}
			section.setLimitNOFILE(value);
			return;
		}
		if (key.equalsIgnoreCase("RemainAfterExit")) {
			section.setRemainAfterExit(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ConditionVirtualization")) {
			if (section.getConditionVirtualization() != null) {
				throw new IllegalArgumentException("переписать ConditionVirtualization в коллекцию");
			}
			section.setConditionVirtualization(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionDirectoryNotEmpty")) {
			section.getConditionDirectoryNotEmpty().add(value);
			return;
		}
		if (key.equalsIgnoreCase("SuccessExitStatus")) {
			if (section.getSuccessExitStatus() != null) {
				throw new IllegalArgumentException("переписать SuccessExitStatus в коллекцию");
			}
			section.setSuccessExitStatus(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathIsSymbolicLink")) {
			if (section.getConditionPathIsSymbolicLink() != null) {
				throw new IllegalArgumentException("переписать ConditionPathIsSymbolicLink в коллекцию");
			}
			section.setConditionPathIsSymbolicLink(value);
			return;
		}
		if (key.equalsIgnoreCase("What")) {
			if (section.getWhat() != null) {
				throw new IllegalArgumentException("переписать What в коллекцию");
			}
			section.setWhat(value);
			return;
		}
		if (key.equalsIgnoreCase("Where")) {
			if (section.getWhere() != null) {
				throw new IllegalArgumentException("переписать Where в коллекцию");
			}
			section.setWhere(value);
			return;
		}
		if (key.equalsIgnoreCase("Options")) {
			if (section.getOptions() != null) {
				throw new IllegalArgumentException("переписать Options в коллекцию");
			}
			section.setOptions(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionFileNotEmpty")) {
			if (section.getConditionFileNotEmpty() != null) {
				throw new IllegalArgumentException("переписать ConditionFileNotEmpty в коллекцию");
			}
			section.setConditionFileNotEmpty(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionNeedsUpdate")) {
			section.getConditionNeedsUpdate().add(value);
			return;
		}
		if (key.equalsIgnoreCase("TimeoutSec")) {
			if (section.getTimeoutSec() != null) {
				throw new NotParsedException("переписать TimeoutSec в коллекцию");
			}
			section.setTimeoutSec(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionFirstBoot")) {
			section.setConditionFirstBoot(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("StandardOutput")) {
			if (section.getStandardOutput() != null) {
				throw new NotParsedException("переписать StandardOutput в коллекцию");
			}
			section.setStandardOutput(value);
			return;
		}
		if (key.equalsIgnoreCase("StandardInput")) {
			if (section.getStandardInput() != null) {
				throw new NotParsedException("переписать StandardInput в коллекцию");
			}
			section.setStandardInput(value);
			return;
		}
		if (key.equalsIgnoreCase("StandardError")) {
			if (section.getStandardError() != null) {
				throw new NotParsedException("переписать StandardError в коллекцию");
			}
			section.setStandardError(value);
			return;
		}
		if (key.equalsIgnoreCase("LoadCredential")) {
			section.getLoadCredential().add(value);
			return;
		}
		if (key.equalsIgnoreCase("RequiresMountsFor")) {
			section.getRequiresMountsFor().add(value);
			return;
		}
		if (key.equalsIgnoreCase("Sockets")) {
			String[] valueArr = value.split(" ");
			for (String socket : valueArr) {
				section.getRequiresMountsFor().add(socket);
			}
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathIsMountPoint")) {
			if (section.getConditionPathIsMountPoint() != null) {
				throw new NotParsedException("переписать ConditionPathIsMountPoint в коллекцию");
			}
			section.setConditionPathIsMountPoint(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionKernelCommandLine")) {
			section.getConditionKernelCommandLine().add(value);
			return;
		}
		if (key.equalsIgnoreCase("RefuseManualStop")) {
			section.setRefuseManualStop(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("KillMode")) {
			section.setKillMode(KillModeType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("TasksMax")) {
			if (section.getTasksMax() != null) {
				throw new NotParsedException("переписать TasksMax в коллекцию");
			}
			section.setTasksMax(value);
			return;
		}
		if (key.equalsIgnoreCase("PrivateMounts")) {
			section.setPrivateMounts(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Environment")) {
			section.getEnvironment().add(value);
			return;
		}
		if (key.equalsIgnoreCase("StopWhenUnneeded")) {
			section.setStopWhenUnneeded(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ConditionFileIsExecutable")) {
			if (section.getConditionFileIsExecutable() != null) {
				throw new NotParsedException("переписать ConditionFileIsExecutable в коллекцию");
			}
			section.setConditionFileIsExecutable(value);
			return;
		}
		if (key.equalsIgnoreCase("Alias")) {
			if (section.getAlias() != null) {
				throw new NotParsedException("переписать Alias в коллекцию");
			}
			section.setAlias(value);
			return;
		}
		if (key.equalsIgnoreCase("RestartPreventExitStatus")) {
			section.setRestartPreventExitStatus(Long.parseLong(value));
			return;
		}
		if (key.equalsIgnoreCase("EnvironmentFile")) {
			section.getEnvironmentFile().add(value);
			return;
		}
		if (key.equalsIgnoreCase("OnActiveSec")) {
			section.setOnActiveSec(Long.parseLong(value));
			return;
		}
		if (key.equalsIgnoreCase("ExecStartPre")) {
			section.getExecStartPre().add(value);
			return;
		}
		if (key.equalsIgnoreCase("Also")) {
			section.getAlso().add(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("ConditionACPower")) {
			section.setConditionACPower(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("User")) {
			if (section.getUser() != null) {
				throw new NotParsedException("переписать User в коллекцию");
			}
			section.setUser(value);
			return;
		}
		if (key.equalsIgnoreCase("SocketUser")) {
			if (section.getSocketUser() != null) {
				throw new NotParsedException("переписать SocketUser в коллекцию");
			}
			section.setSocketUser(value);
			return;
		}
		if (key.equalsIgnoreCase("Nice")) {
			section.setNice(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("IOSchedulingClass")) {
			section.setIOSchedulingClass(IOSchedulingClassType.valueOf(value.replace("-", "_")));
			return;
		}
		if (key.equalsIgnoreCase("IOSchedulingPriority")) {
			section.setIOSchedulingPriority(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("NotifyAccess")) {
			section.setNotifyAccess(NotifyAccessType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("PrivateNetwork")) {
			section.setPrivateNetwork(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("PrivateDevices")) {
			section.setPrivateDevices(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("CacheDirectory")) {
			if (section.getCacheDirectory() != null) {
				throw new NotParsedException("переписать CacheDirectory в коллекцию");
			}
			section.setCacheDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("PrivateUsers")) {
			section.setPrivateUsers(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ProtectKernelTunables")) {
			section.setProtectKernelTunables(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("RemoveIPC")) {
			section.setRemoveIPC(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ReadOnlyPaths")) {
			if (section.getReadOnlyPaths() != null) {
				throw new NotParsedException("переписать ReadOnlyPaths в коллекцию");
			}
			section.setReadOnlyPaths(value);
			return;
		}
		if (key.equalsIgnoreCase("UMask")) {
			if (section.getUMask() != null) {
				throw new IllegalArgumentException("переписать UMask в коллекцию");
			}
			section.setUMask(value);
			return;
		}
		if (key.equalsIgnoreCase("TimeoutStopSec")) {
			if (section.getTimeoutStopSec() != null) {
				throw new IllegalArgumentException("переписать TimeoutStopSec в коллекцию");
			}
			section.setTimeoutStopSec(value);
			return;
		}
		if (key.equalsIgnoreCase("Group")) {
			if (section.getGroup() != null) {
				throw new IllegalArgumentException("переписать Group в коллекцию");
			}
			section.setGroup(value);
			return;
		}
		if (key.equalsIgnoreCase("AmbientCapabilities")) {
			String[] valueArr = value.split(" ");
			for (String cap : valueArr) {
				section.setAmbientCapabilities(CAPs.valueOf(cap.toUpperCase()));
			}
			return;
		}
		if (key.equalsIgnoreCase("DynamicUser")) {
			section.setDynamicUser(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("OnFailure")) {
			if (section.getOnFailure() != null) {
				throw new IllegalArgumentException("переписать Group в коллекцию");
			}
			section.setOnFailure(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("WorkingDirectory")) {
			if (section.getWorkingDirectory() != null) {
				throw new IllegalArgumentException("переписать WorkingDirectory в коллекцию");
			}
			section.setWorkingDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("CPUSchedulingPolicy")) {
			if (section.getCPUSchedulingPolicy() != null) {
				throw new IllegalArgumentException("переписать CPUSchedulingPolicy в коллекцию");
			}
			section.setCPUSchedulingPolicy(value);
			return;
		}
		if (key.equalsIgnoreCase("SyslogIdentifier")) {
			if (section.getSyslogIdentifier() != null) {
				throw new IllegalArgumentException("переписать SyslogIdentifier в коллекцию");
			}
			section.setSyslogIdentifier(value);
			return;
		}
		if (key.equalsIgnoreCase("SupplementaryGroups")) {
			section.getSupplementaryGroups().add(value);
			return;
		}
		if (key.equalsIgnoreCase("LimitMEMLOCK")) {
			if (section.getLimitMEMLOCK() != null) {
				throw new IllegalArgumentException("переписать LimitMEMLOCK в коллекцию");
			}
			section.setLimitMEMLOCK(value);
			return;
		}
		if (key.equalsIgnoreCase("PathExists")) {
			if (section.getPathExists() != null) {
				throw new IllegalArgumentException("переписать PathExists в коллекцию");
			}
			section.setPathExists(value);
			return;
		}
		if (key.equalsIgnoreCase("IgnoreSIGPIPE")) {
			section.setIgnoreSIGPIPE(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("FailureAction")) {
			if (section.getFailureAction() != null) {
				throw new IllegalArgumentException("переписать FailureAction в коллекцию");
			}
			section.setFailureAction(value);
			return;
		}
		if (key.equalsIgnoreCase("CPUSchedulingPriority")) {
			section.setCPUSchedulingPriority(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("PermissionsStartOnly")) {
			section.setPermissionsStartOnly(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("TimeoutStartSec")) {
			if (section.getTimeoutStartSec() != null) {
				throw new IllegalArgumentException("переписать TimeoutStartSec в коллекцию");
			}
			section.setTimeoutStartSec(value);
			return;
		}
		if (key.equalsIgnoreCase("RuntimeDirectoryMode")) {
			if (section.getRuntimeDirectoryMode() != null) {
				throw new IllegalArgumentException("переписать RuntimeDirectoryMode в коллекцию");
			}
			section.setRuntimeDirectoryMode(value);
			return;
		}
		if (key.equalsIgnoreCase("ConfigurationDirectory")) {
			if (section.getConfigurationDirectory() != null) {
				throw new IllegalArgumentException("переписать ConfigurationDirectory в коллекцию");
			}
			section.setConfigurationDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("ConfigurationDirectoryMode")) {
			if (section.getConfigurationDirectoryMode() != null) {
				throw new IllegalArgumentException("переписать ConfigurationDirectoryMode в коллекцию");
			}
			section.setConfigurationDirectoryMode(value);
			return;
		}
		if (key.equalsIgnoreCase("StateDirectoryMode")) {
			if (section.getStateDirectoryMode() != null) {
				throw new IllegalArgumentException("переписать StateDirectoryMode в коллекцию");
			}
			section.setStateDirectoryMode(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathExistsGlob")) {
			section.getConditionPathExistsGlob().add(value);
			return;
		}
		if (key.equalsIgnoreCase("SocketGroup")) {
			if (section.getSocketGroup() != null) {
				throw new IllegalArgumentException("переписать SocketGroup в коллекцию");
			}
			section.setSocketGroup(value);
			return;
		}
		if (key.equalsIgnoreCase("LimitRTPRIO")) {
			section.setLimitRTPRIO(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("FileDescriptorName")) {
			if (section.getFileDescriptorName() != null) {
				throw new IllegalArgumentException("переписать FileDescriptorName в коллекцию");
			}
			section.setFileDescriptorName(value);
			return;
		}
		if (key.equalsIgnoreCase("ExecStartPost")) {
			if (section.getExecStartPost() != null) {
				throw new IllegalArgumentException("переписать ExecStartPost в коллекцию");
			}
			section.setExecStartPost(value);
			return;
		}
		if (key.equalsIgnoreCase("KillSignal")) {
			section.setKillSignal(KillSignalType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("SendSIGKILL")) {
			section.setSendSIGKILL(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ExecStopPost")) {
			if (section.getExecStopPost() != null) {
				throw new IllegalArgumentException("переписать ExecStopPost в коллекцию");
			}
			section.setExecStopPost(value);
			return;
		}
		if (key.equalsIgnoreCase("BindsTo")) {
			if (section.getBindsTo() != null) {
				throw new IllegalArgumentException("переписать BindsTo в коллекцию");
			}
			section.setBindsTo(value);
			return;
		}
		if (key.equalsIgnoreCase("RefuseManualStart")) {
			section.setRefuseManualStart(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Unit")) {
			if (section.getUnit() != null) {
				throw new IllegalArgumentException("переписать Unit в коллекцию");
			}
			section.setUnit(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("StartLimitIntervalSec")) {
			section.setStartLimitIntervalSec(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("StartLimitBurst")) {
			section.setStartLimitBurst(Integer.parseInt(value));
			return;
		}
		if (key.equalsIgnoreCase("Delegate")) {
			if (section.getDelegate() != null) {
				throw new IllegalArgumentException("переписать Delegate в коллекцию");
			}
			section.setDelegate(value);
			return;
		}
		if (key.equalsIgnoreCase("LimitNPROC")) {
			if (section.getLimitNPROC() != null) {
				throw new IllegalArgumentException("переписать LimitNPROC в коллекцию");
			}
			section.setLimitNPROC(value);
			return;
		}
		if (key.equalsIgnoreCase("LimitCORE")) {
			if (section.getLimitCORE() != null) {
				throw new IllegalArgumentException("переписать LimitCORE в коллекцию");
			}
			section.setLimitCORE(value);
			return;
		}
		if (key.equalsIgnoreCase("DevicePolicy")) {
			section.setDevicePolicy(DevicePolicyType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("CPUAccounting")) {
			section.setCPUAccounting(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("MemoryAccounting")) {
			section.setMemoryAccounting(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("RequiredBy")) {
			section.getRequiredBy().add(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("SuccessAction")) {
			section.setSuccessAction(SuccessActionType.valueOf(value.replace("-", "_")));
			return;
		}
		if (key.equalsIgnoreCase("RestartKillSignal")) {
			section.setRestartKillSignal(RestartKillSignalType.valueOf(value));
			return;
		}
		if (key.equalsIgnoreCase("AllowIsolate")) {
			section.setAllowIsolate(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("BindIPv6Only")) {
			section.setBindIPv6Only(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("AssertPathExists")) {
			if (section.getAssertPathExists() != null) {
				throw new IllegalArgumentException("переписать AssertPathExists в коллекцию");
			}
			section.setAssertPathExists(value);
			return;
		}
		if (key.equalsIgnoreCase("LogsDirectory")) {
			if (section.getLogsDirectory() != null) {
				throw new IllegalArgumentException("переписать LogsDirectory в коллекцию");
			}
			section.setLogsDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("Slice")) {
			section.setSlice(new SLICE().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("LogsDirectoryMode")) {
			if (section.getLogsDirectoryMode() != null) {
				throw new IllegalArgumentException("переписать LogsDirectoryMode в коллекцию");
			}
			section.setLogsDirectoryMode(value);
			return;
		}
		if (key.equalsIgnoreCase("UtmpIdentifier")) {
			if (section.getUtmpIdentifier() != null) {
				throw new IllegalArgumentException("переписать UtmpIdentifier в коллекцию");
			}
			section.setUtmpIdentifier(value);
			return;
		}
		if (key.equalsIgnoreCase("PartOf")) {
			if (section.getPartOf() != null) {
				throw new IllegalArgumentException("переписать PartOf в коллекцию");
			}
			section.setPartOf(new SystemdElement().fileName(value));
			return;
		}
		if (key.equalsIgnoreCase("ReadOnlyDirectories")) {
			if (section.getReadOnlyDirectories() != null) {
				throw new IllegalArgumentException("переписать ReadOnlyDirectories в коллекцию");
			}
			section.setReadOnlyDirectories(value);
			return;
		}
		if (key.equalsIgnoreCase("ReadWriteDirectories")) {
			section.getReadWriteDirectories().add(value);
			return;
		}
		if (key.equalsIgnoreCase("PassPacketInfo")) {
			section.setPassPacketInfo(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("TTYPath")) {
			if (section.getTTYPath() != null) {
				throw new IllegalArgumentException("переписать TTYPath в коллекцию");
			}
			section.setTTYPath(value);
			return;
		}
		if (key.equalsIgnoreCase("OnFailureJobMode")) {
			section.setOnFailureJobMode(OnFailureJobModeType.valueOf(value.replace("-", "_").toUpperCase()));
			return;
		}
		if (key.equalsIgnoreCase("RuntimeMaxSec")) {
			if (section.getRuntimeMaxSec() != null) {
				throw new IllegalArgumentException("переписать RuntimeMaxSec в коллекцию");
			}
			section.setRuntimeMaxSec(value);
			return;
		}
		if (key.equalsIgnoreCase("NonBlocking")) {
			section.setNonBlocking(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("PAMName")) {
			if (section.getPAMName() != null) {
				throw new IllegalArgumentException("переписать PAMName в коллекцию");
			}
			section.setPAMName(value);
			return;
		}
		if (key.equalsIgnoreCase("ConditionPathIsDirectory")) {
			if (section.getConditionPathIsDirectory() != null) {
				throw new IllegalArgumentException("переписать ConditionPathIsDirectory в коллекцию");
			}
			section.setConditionPathIsDirectory(value);
			return;
		}
		if (key.equalsIgnoreCase("SendSIGHUP")) {
			section.setSendSIGHUP(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("TTYReset")) {
			section.setTTYReset(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("ConditionControlGroupController")) {
			section.getConditionControlGroupController().add(value);
			return;
		}
		if (key.equalsIgnoreCase("JobTimeoutSec")) {
			if (section.getJobTimeoutSec() != null) {
				throw new IllegalArgumentException("переписать JobTimeoutSec в коллекцию");
			}
			section.setJobTimeoutSec(value);
			return;
		}
		if (key.equalsIgnoreCase("JobTimeoutAction")) {
			section.setJobTimeoutAction(JobTimeoutActionType.valueOf(value.replaceAll("-", "_").toUpperCase()));
			return;
		}
		if (key.equalsIgnoreCase("KeyringMode")) {
			section.setKeyringMode(KeyringModeType.valueOf(value.replaceAll("-", "_").toUpperCase()));
			return;
		}
		if (key.equalsIgnoreCase("ListenSpecial")) {
			if (section.getListenSpecial() != null) {
				throw new IllegalArgumentException("переписать ListenSpecial в коллекцию");
			}
			section.setListenSpecial(value);
			return;
		}
		if (key.equalsIgnoreCase("TTYVHangup")) {
			section.setTTYVHangup(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("MemoryMin")) {
			if (section.getMemoryMin() != null) {
				throw new IllegalArgumentException("переписать MemoryMin в коллекцию");
			}
			section.setMemoryMin(value);
			return;
		}
		if (key.equalsIgnoreCase("UnsetEnvironment")) {
			String[] valueArr = value.split(" ");
			for (String envVar : valueArr) {
				section.getUnsetEnvironment().add(envVar);
			}
			return;
		}
		if (key.equalsIgnoreCase("TTYVTDisallocate")) {
			section.setTTYVTDisallocate(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("Writable")) {
			section.setWritable(parseYesNoBoolean(value));
			return;
		}
		if (key.equalsIgnoreCase("MemoryLow")) {
			if (section.getMemoryLow() != null) {
				throw new IllegalArgumentException("переписать MemoryLow в коллекцию");
			}
			section.setMemoryLow(value);
			return;
		}
		if (key.equalsIgnoreCase("DefaultInstance")) {
			if (section.getDefaultInstance() != null) {
				throw new IllegalArgumentException("переписать DefaultInstance в коллекцию");
			}
			section.setDefaultInstance(value);
			return;
		}
		if (key.equalsIgnoreCase("StartLimitInterval")) {
			if (section.getStartLimitInterval() != null) {
				throw new IllegalArgumentException("переписать StartLimitInterval в коллекцию");
			}
			section.setStartLimitInterval(value);
			return;
		}
		if (key.equalsIgnoreCase("ProcSubset")) {
			if (section.getProcSubset() != null) {
				throw new IllegalArgumentException("переписать ProcSubset в коллекцию");
			}
			section.setProcSubset(value);
			return;
		}
		if (key.equalsIgnoreCase("RestartForceExitStatus")) {
			section.setRestartForceExitStatus(Integer.parseInt(value));
			return;
		}
		// TODO

		// error
		String msg = String.format("withSection %s key [%s] value [%s]", Section.retNameFor(section.getName()), key,
				value);
		throw new NotParsedException(msg);

	}

	private Boolean parseYesNoBoolean(String value) throws IllegalArgumentException {
		boolean ret;
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on")) {
			ret = true;
		} else if (value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false")) {
			ret = false;
		} else {
			throw new IllegalArgumentException(value);
		}
		return ret;
	}

}
