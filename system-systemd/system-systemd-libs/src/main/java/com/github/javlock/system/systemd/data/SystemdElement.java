package com.github.javlock.system.systemd.data;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.javlock.system.apidata.exceptions.ObjectTypeException;
import com.github.javlock.system.apidata.systemd.data.SystDKeyMeta;
import com.github.javlock.system.systemd.data.sections.impl.AUTOMOUNTSection;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.MOUNTSection;
import com.github.javlock.system.systemd.data.sections.impl.PathSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.TimerSection;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.utils.ServiceUtils;
import com.github.javlock.system.systemd.utils.slice.SLICE;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class SystemdElement {

	public enum CAPs {
		CAP_SYS_ADMIN, CAP_MAC_ADMIN, CAP_AUDIT_CONTROL, CAP_SYS_RAWIO, CAP_CHOWN, CAP_DAC_READ_SEARCH,
		CAP_DAC_OVERRIDE, CAP_FOWNER, CAP_SYS_TTY_CONFIG, CAP_LINUX_IMMUTABLE, CAP_SYS_PTRACE, CAP_SYSLOG,
		CAP_AUDIT_READ, CAP_SETUID, CAP_SETGID, CAP_MAC_OVERRIDE, CAP_SYS_NICE, CAP_SYS_CHROOT, CAP_NET_ADMIN,
		CAP_NET_BIND_SERVICE, CAP_NET_RAW, CAP_SYS_MODULE, CAP_IPC_LOCK, CAP_AUDIT_WRITE, CAP_KILL, CAP_NET_BROADCAST,
		CAP_SETPCAP, CAP_SYS_TIME, CAP_FSETID, CAP_MKNOD, CAP_SETFCAP, CAP_SYS_RESOURCE

		, TILDE_CAP_SYS_PTRACE, CAP_BLOCK_SUSPEND
	}

	public enum ConditionSecurityType {
		selinux, apparmor, tomoyo, ima, smack, audit, tpm2, uefi_secureboot
	}

	public enum DefaultDependenciesType {
		no
	}

	public enum DevicePolicyType {
		closed
	}

	public enum ELEMENTTYPE {
		SERVICE, TARGET, TIMER, SOCKET, PATH, SLICE, MOUNT, AUTOMOUNT, CONF, DEVICE
	}

	public enum IOSchedulingClassType {
		best_effort, idle
	}

	public enum JobTimeoutActionType {
		POWEROFF_FORCE, REBOOT_FORCE
	}

	public enum KeyringModeType {
		INHERIT
	}

	public enum KillModeType {
		mixed, none, process
	}

	public enum KillSignalType {
		SIGHUP, SIGTERM, SIGINT, SIGQUIT
	}

	public enum NotifyAccessType {
		all, main
	}

	public enum OnFailureJobModeType {
		REPLACE_IRREVERSIBLY
	}

	public enum ProtectProcType {
		invisible
	}

	public enum RestartKillSignalType {
		SIGUSR2
	}

	public enum RestartType {
		always, no, on_abort, on_failure, on_success
	}

	public enum RestrictAddressFamiliesType {
		AF_UNIX, AF_NETLINK, AF_INET, AF_INET6, AF_QIPCRTR, AF_LOCAL, AF_ALG, AF_PACKET

	}

	public enum SECTIONNAME {
		NotConfigured, Service, Install, PATH, AUTOMOUNT, Unit, Socket, Timer, Mount, Slice
	}

	public enum SuccessActionType {
		reboot, reboot_force, exit_force, poweroff_force
	}

	public enum Type {
		binfmt_misc, btrfs, configfs, dbus, debugfs, exec, forking, fusectl, hugetlbfs, idle, mqueue, notify, oneshot,
		simple, tmpfs, tracefs
	}

	// SECTIONs
	private @Setter @Getter UnitSection unitSection;// create-ok
	private @Setter @Getter ServiceSection serviceSection;// create-ok
	private @Setter InstallSection installSection;
	private @Setter PathSection pathSection;
	private @Setter SocketSection socketSection;
	private @Setter TimerSection timerSection;
	private @Setter AUTOMOUNTSection automountSection;
	private @Setter SliceSection sliceSection;
	private @Setter MOUNTSection mountSection;
	// for constr
	private @Getter @Setter SECTIONNAME name = SECTIONNAME.NotConfigured;
	private @Getter @Setter String fileName;
	private @Getter @Setter ELEMENTTYPE elementType;

	// ALL
	// out
	private @Getter CopyOnWriteArrayList<String> execStart = new CopyOnWriteArrayList<>();// +
	private @Getter CopyOnWriteArrayList<String> execStartPre = new CopyOnWriteArrayList<>();// +
	private @Getter CopyOnWriteArrayList<String> execStop = new CopyOnWriteArrayList<>();// +
	private @Getter CopyOnWriteArrayList<SystemdElement> after = new CopyOnWriteArrayList<>();// +
	private @Getter CopyOnWriteArrayList<SystemdElement> before = new CopyOnWriteArrayList<>();// +
	private @Getter @Setter String description;// +
	private @Getter @Setter String execStartPost;// +
	private @Getter @Setter String execStopPost;// +
	private @Getter @Setter Type typeVar;// +
	private @Deprecated @Getter @Setter String cPUSchedulingPolicy;// + type
	private @Deprecated @Getter @Setter String fileDescriptorName;// + type
	private @Deprecated @Getter @Setter String iPAddressDeny;// + TODO replace
	private @Getter CopyOnWriteArrayList<SystemdElement> wantedBy = new CopyOnWriteArrayList<>();// +
	private @Getter CopyOnWriteArrayList<CAPs> capabilityBoundingSet = new CopyOnWriteArrayList<>(); // OL +

	// out

	// TODO start

	private @Deprecated @Getter @Setter String timestamping;// TODO replace
	private @Getter CopyOnWriteArrayList<CAPs> conditionCapability = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> conditionControlGroupController = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> conditionDirectoryNotEmpty = new CopyOnWriteArrayList<>();// ?
	private @Getter CopyOnWriteArrayList<String> conditionKernelCommandLine = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> conditionNeedsUpdate = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> conditionPathExistsGlob = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> conditionPathExists = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> deviceAllow = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> documentation = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> environmentFile = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> environment = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> execReload = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> listenDatagram = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> listenStream = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> loadCredential = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> readWriteDirectories = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> readWritePaths = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> requiresMountsFor = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> sockets = new CopyOnWriteArrayList<>();// OL
	private @Getter CopyOnWriteArrayList<String> supplementaryGroups = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> symlinks = new CopyOnWriteArrayList<>();// OL
	private @Getter CopyOnWriteArrayList<String> systemCallFilter = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> unsetEnvironment = new CopyOnWriteArrayList<>();// OL
	private @Getter CopyOnWriteArrayList<SystemdElement> also = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> conflicts = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> requiredBy = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> requires = new CopyOnWriteArrayList<>();// ML

	private @Getter CopyOnWriteArrayList<SystemdElement> wants = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Boolean accept;
	private @Getter @Setter Boolean allowIsolate;
	private @Getter @Setter Boolean bindIPv6Only;
	private @Getter @Setter Boolean conditionACPower;
	private @Getter @Setter Boolean conditionFirstBoot;
	private @Getter @Setter Boolean cPUAccounting;
	private @Getter @Setter Boolean dynamicUser;
	private @Getter @Setter Boolean ignoreOnIsolate;
	private @Getter @Setter Boolean ignoreSIGPIPE;
	private @Getter @Setter Boolean lockPersonality;
	private @Getter @Setter Boolean makeDirectory;
	private @Getter @Setter Boolean memoryAccounting;
	private @Getter @Setter Boolean memoryDenyWriteExecute;
	private @Getter @Setter Boolean nonBlocking;
	private @Getter @Setter Boolean noNewPrivileges;
	private @Getter @Setter Boolean passCredentials;
	private @Getter @Setter Boolean passPacketInfo;
	private @Getter @Setter Boolean passSecurity;
	private @Getter @Setter Boolean permissionsStartOnly;
	private @Getter @Setter Boolean persistent;
	private @Getter @Setter Boolean privateDevices;
	private @Getter @Setter Boolean privateMounts;
	private @Getter @Setter Boolean privateNetwork;
	private @Getter @Setter Boolean privateTmp;
	private @Getter @Setter Boolean privateUsers;
	private @Getter @Setter Boolean protectClock;
	private @Getter @Setter Boolean protectControlGroups;
	private @Getter @Setter Boolean protectHostname;
	private @Getter @Setter Boolean protectKernelLogs;
	private @Getter @Setter Boolean protectKernelModules;
	private @Getter @Setter Boolean protectKernelTunables;
	private @Getter @Setter Boolean refuseManualStart;
	private @Getter @Setter Boolean refuseManualStop;
	private @Getter @Setter Boolean remainAfterExit;
	private @Getter @Setter Boolean removeIPC;
	private @Getter @Setter Boolean removeOnStop;
	private @Getter @Setter Boolean restrictRealtime;
	private @Getter @Setter Boolean restrictSUIDSGID;
	private @Getter @Setter Boolean runtimeDirectoryPreserve;
	private @Getter @Setter Boolean sendSIGHUP;
	private @Getter @Setter Boolean sendSIGKILL;
	private @Getter @Setter Boolean stopWhenUnneeded;
	private @Getter @Setter Boolean tTYReset;
	private @Getter @Setter Boolean tTYVHangup;
	private @Getter @Setter Boolean tTYVTDisallocate;
	private @Getter @Setter Boolean writable;
	private @Getter @Setter CAPs ambientCapabilities;
	private @Getter @Setter ConditionSecurityType conditionSecurity;
	private @Getter @Setter CopyOnWriteArrayList<RestrictAddressFamiliesType> restrictAddressFamilies = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> listenFIFO = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter DefaultDependenciesType defaultDependencies;
	private @Getter @Setter DevicePolicyType devicePolicy;
	private @Getter @Setter Integer cPUSchedulingPriority;
	private @Getter @Setter Integer iOSchedulingPriority;
	private @Getter @Setter Integer limitRTPRIO;
	private @Getter @Setter Integer nice;
	private @Getter @Setter Integer restartForceExitStatus;
	private @Getter @Setter Integer startLimitBurst;
	private @Getter @Setter Integer startLimitIntervalSec;
	private @Getter @Setter IOSchedulingClassType iOSchedulingClass;
	private @Getter @Setter JobTimeoutActionType jobTimeoutAction;
	private @Getter @Setter KeyringModeType keyringMode;
	private @Getter @Setter KillModeType killMode;
	private @Getter @Setter KillSignalType killSignal;
	private @Getter @Setter Long fileDescriptorStoreMax;
	private @Getter @Setter Long maxConnections;
	private @Getter @Setter Long onActiveSec;
	private @Getter @Setter Long restartPreventExitStatus;
	private @Getter @Setter NotifyAccessType notifyAccess;
	private @Getter @Setter OnFailureJobModeType onFailureJobMode;
	private @Getter @Setter ProtectProcType protectProc;
	private @Getter @Setter RestartKillSignalType restartKillSignal;
	private @Getter @Setter RestartType restart;
	private @Getter @Setter SLICE slice;
	private @Getter @Setter String accuracySec;
	private @Getter @Setter String alias;
	private @Getter @Setter String assertPathExists;
	private @Getter @Setter String bindsTo;
	private @Getter @Setter String busName;
	private @Getter @Setter String cacheDirectory;
	private @Getter @Setter String conditionFileIsExecutable;
	private @Getter @Setter String conditionFileNotEmpty;
	private @Getter @Setter String conditionPathIsDirectory;
	private @Getter @Setter String conditionPathIsMountPoint;
	private @Getter @Setter String conditionPathIsReadWrite;
	private @Getter @Setter String conditionPathIsSymbolicLink;
	private @Getter @Setter String conditionVirtualization;
	private @Getter @Setter String configurationDirectory;
	private @Getter @Setter String configurationDirectoryMode;
	private @Getter @Setter String defaultInstance;
	private @Getter @Setter String delegate;
	private @Getter @Setter String directoryNotEmpty;
	private @Getter @Setter String failureAction;
	private @Getter @Setter String group;
	private @Getter @Setter String jobTimeoutSec;
	private @Getter @Setter String limitCORE;
	private @Getter @Setter String limitMEMLOCK;
	private @Getter @Setter String limitNOFILE;
	private @Getter @Setter String limitNPROC;
	private @Getter @Setter String listenNetlink;
	private @Getter @Setter String listenSequentialPacket;
	private @Getter @Setter String listenSpecial;
	private @Getter @Setter String logsDirectory;
	private @Getter @Setter String logsDirectoryMode;
	private @Getter @Setter String memoryLow;
	private @Getter @Setter String memoryMin;
	private @Getter @Setter String onBootSec;
	private @Getter @Setter String onCalendar;
	private @Getter @Setter String onUnitActiveSec;
	private @Getter @Setter String oOMScoreAdjust;
	private @Getter @Setter String options;
	private @Getter @Setter String pAMName;
	private @Getter @Setter String pathExists;
	private @Getter @Setter String pIDFile;
	private @Getter @Setter String procSubset;
	private @Getter @Setter String protectHome;
	private @Getter @Setter String protectSystem;
	private @Getter @Setter String randomizedDelaySec;
	private @Getter @Setter String readOnlyDirectories;
	private @Getter @Setter String readOnlyPaths;
	private @Getter @Setter String receiveBuffer;
	private @Getter @Setter String restartSec;
	private @Getter @Setter String restrictNamespaces;
	private @Getter @Setter String runtimeDirectory;
	private @Getter @Setter String runtimeDirectoryMode;
	private @Getter @Setter String runtimeMaxSec;
	private @Getter @Setter String sendBuffer;
	private @Getter @Setter String socketGroup;
	private @Getter @Setter String socketMode;
	private @Getter @Setter String socketUser;
	private @Getter @Setter String standardError;
	private @Getter @Setter String standardInput;
	private @Getter @Setter String standardOutput;
	private @Getter @Setter String startLimitInterval;
	private @Getter @Setter String stateDirectory;
	private @Getter @Setter String stateDirectoryMode;
	private @Getter @Setter String successExitStatus;
	private @Getter @Setter String syslogIdentifier;
	private @Getter @Setter String systemCallArchitectures;
	private @Getter @Setter String systemCallErrorNumber;
	private @Getter @Setter String tasksMax;
	private @Getter @Setter String timeoutSec;
	private @Getter @Setter String timeoutStartSec;
	private @Getter @Setter String timeoutStopSec;
	private @Getter @Setter String tTYPath;
	private @Getter @Setter String uMask;
	private @Getter @Setter String user;
	private @Getter @Setter String utmpIdentifier;
	private @Getter @Setter String watchdogSec;
	private @Getter @Setter String what;
	private @Getter @Setter String where;
	private @Getter @Setter String workingDirectory;
	private @Getter @Setter SuccessActionType successAction;
	private @Getter @Setter SystemdElement onFailure;
	private @Getter @Setter SystemdElement partOf;
	private @Getter @Setter SystemdElement service;
	private @Getter @Setter SystemdElement unit;

	// TODO END

	public SystemdElement fileName(String name) throws IllegalArgumentException, ObjectTypeException {
		elementType = ServiceUtils.getElementType(name);
		fileName = name;
		return this;
	}

	public AUTOMOUNTSection getAutomountSection() {
		if (automountSection == null) {
			automountSection = new AUTOMOUNTSection();
		}
		return automountSection;
	}

	public InstallSection getInstallSection() {
		if (installSection == null) {
			installSection = new InstallSection();
		}
		return installSection;
	}

	public MOUNTSection getMountSection() {
		if (mountSection == null) {
			mountSection = new MOUNTSection();
		}
		return mountSection;
	}

	public PathSection getOrCreatePathSection() {
		if (pathSection == null) {
			pathSection = new PathSection();
		}
		return pathSection;
	}

	public ServiceSection getOrCreateServiceSection() {
		if (serviceSection == null) {
			serviceSection = new ServiceSection();
		}
		return serviceSection;
	}

	public SliceSection getOrCreateSliceSection() {
		if (sliceSection == null) {
			sliceSection = new SliceSection();
		}
		return sliceSection;
	}

	public SocketSection getOrCreateSocketSection() {
		if (socketSection == null) {
			socketSection = new SocketSection();
		}
		return socketSection;
	}

	public TimerSection getOrCreateTimerSection() {
		if (timerSection == null) {
			timerSection = new TimerSection();
		}
		return timerSection;
	}

	public UnitSection getOrCreateUnitSection() {
		if (unitSection == null) {
			unitSection = new UnitSection();
		}
		return unitSection;
	}

	public String retName() {
		return "[" + getName() + "]";
	}

	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');

		if (description != null) {
			builder.append("Description=").append(description).append('\n');
		}
		if (!getBefore().isEmpty()) {
			for (SystemdElement beforElement : getBefore()) {
				builder.append("Before=").append(beforElement.getFileName()).append('\n');
			}
		}
		if (!getAfter().isEmpty()) {
			for (SystemdElement afterElement : getAfter()) {
				builder.append("After=").append(afterElement.getFileName()).append('\n');
			}
		}
		if (typeVar != null) {
			builder.append("Type=").append(typeVar).append('\n');
		}

		if (!getExecStartPre().isEmpty()) {
			for (String execStartPreLocal : getExecStartPre()) {
				builder.append("ExecStartPre=").append(execStartPreLocal).append('\n');
			}
		}
		if (!getExecStart().isEmpty()) {
			for (String execstart : getExecStart()) {
				builder.append("ExecStart=").append(execstart).append('\n');
			}
		}
		if (getExecStartPost() != null) {
			builder.append("ExecStartPost=").append(getExecStartPost()).append('\n');
		}
		if (!getExecReload().isEmpty()) {
			for (String reload : getExecReload()) {
				builder.append("ExecReload=").append(reload).append('\n');
			}
		}
		//
		if (!getExecStop().isEmpty()) {
			for (String execstop : getExecStop()) {
				builder.append("ExecStop=").append(execstop).append('\n');
			}
		}
		if (getExecStopPost() != null) {
			builder.append("ExecStopPost=").append(getExecStopPost()).append('\n');
		}

		if (getCPUSchedulingPolicy() != null) {
			builder.append("CPUSchedulingPolicy=").append(getCPUSchedulingPolicy()).append('\n');
		}
		if (getFileDescriptorName() != null) {
			builder.append("FileDescriptorName=").append(getFileDescriptorName()).append('\n');
		}
		if (getIPAddressDeny() != null) {
			builder.append("IPAddressDeny=").append(getIPAddressDeny()).append('\n');
		}
		if (getTimestamping() != null) {
			builder.append("Timestamping=").append(getTimestamping()).append('\n');
		}
		if (!getWantedBy().isEmpty()) {
			for (SystemdElement systemdElement : getWantedBy()) {
				builder.append(SystDKeyMeta.addKeyTo(SystDKeyMeta.WANTEDBY)).append(systemdElement.getFileName())
						.append('\n');
			}
		}

		// capabilityBoundingSet
		if (!getCapabilityBoundingSet().isEmpty()) {
			builder.append("CapabilityBoundingSet=");
			for (CAPs cap : getCapabilityBoundingSet()) {
				String capval = cap.toString();
				if (cap.toString().toLowerCase().contains("TILDE_")) {
					capval = cap.toString().replace("TILDE_", "~");
				}
				builder.append(capval).append(" ");
			}
			builder.append('\n');
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
