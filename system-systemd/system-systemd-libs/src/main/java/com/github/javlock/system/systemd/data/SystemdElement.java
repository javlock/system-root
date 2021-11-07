package com.github.javlock.system.systemd.data;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.javlock.system.systemd.data.sections.AUTOMOUNTSection;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
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
		selinux, apparmor, tomoyo, ima, smack, audit, tpm2,

		uefi_secureboot// TODO

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
	private @Setter UnitSection unitSection;
	private @Setter ServiceSection serviceSection;

	private @Setter InstallSection installSection;
	private @Setter PathSection pathSection;
	private @Setter SocketSection socketSection;
	private @Setter TimerSection timerSection;
	private @Setter AUTOMOUNTSection automountSection;
	private @Setter SliceSection sliceSection;
	private @Setter MOUNTSection mountSection;
	//
	private @Getter @Setter SECTIONNAME name = SECTIONNAME.NotConfigured;
	//
	private @Getter @Setter String fileName;
	private @Getter @Setter ELEMENTTYPE elementType;
	// ALL
	private @Getter @Setter String description;
	private @Getter @Setter Type typeVar;
	private @Getter CopyOnWriteArrayList<String> execStart = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> execStop = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> execReload = new CopyOnWriteArrayList<>();
	private @Getter @Setter RestartType Restart;
	private @Getter @Setter String PIDFile;
	private @Getter CopyOnWriteArrayList<SystemdElement> WantedBy = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> Documentation = new CopyOnWriteArrayList<>();
	private @Getter @Setter String OnCalendar;
	private @Getter @Setter String AccuracySec;
	private @Getter @Setter Boolean Persistent;
	private @Getter @Setter String RandomizedDelaySec;
	private @Getter CopyOnWriteArrayList<String> ConditionPathExists = new CopyOnWriteArrayList<>();
	private @Getter @Setter String OnBootSec;
	private @Getter @Setter String OnUnitActiveSec;
	private @Getter CopyOnWriteArrayList<String> ListenStream = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter DefaultDependenciesType DefaultDependencies;
	private @Getter @Setter CopyOnWriteArrayList<String> ListenFIFO = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String SocketMode;
	private @Getter @Setter Boolean RemoveOnStop;
	private @Getter CopyOnWriteArrayList<SystemdElement> Before = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> Conflicts = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String ListenSequentialPacket;
	private @Getter @Setter Boolean Accept;
	private @Getter @Setter Long MaxConnections;
	private @Getter @Setter ConditionSecurityType ConditionSecurity;
	private @Getter CopyOnWriteArrayList<CAPs> ConditionCapability = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter SystemdElement Service;
	private @Getter @Setter String ReceiveBuffer;
	private @Getter @Setter String SendBuffer;
	private @Getter @Setter String ListenNetlink;
	private @Getter @Setter Boolean PassCredentials;
	private @Getter @Setter Boolean IgnoreOnIsolate;
	private @Getter CopyOnWriteArrayList<String> ListenDatagram = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Boolean PassSecurity;
	private @Getter CopyOnWriteArrayList<String> Symlinks = new CopyOnWriteArrayList<>();// OL
	private @Deprecated @Getter @Setter String Timestamping;// TODO replace
	private @Getter @Setter String ConditionPathIsReadWrite;
	private @Getter CopyOnWriteArrayList<SystemdElement> Requires = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String OOMScoreAdjust;
	private @Getter @Setter String DirectoryNotEmpty;
	private @Getter @Setter Boolean MakeDirectory;
	private @Getter CopyOnWriteArrayList<SystemdElement> Wants = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> After = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String BusName;
	private @Getter CopyOnWriteArrayList<CAPs> CapabilityBoundingSet = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> DeviceAllow = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Long FileDescriptorStoreMax;
	private @Deprecated @Getter @Setter String IPAddressDeny;// TODO replace
	private @Getter @Setter Boolean LockPersonality;
	private @Getter @Setter Boolean MemoryDenyWriteExecute;
	private @Getter @Setter Boolean NoNewPrivileges;
	private @Getter @Setter Boolean PrivateTmp;
	private @Getter @Setter ProtectProcType ProtectProc;

	private @Getter @Setter Boolean ProtectClock;
	private @Getter @Setter Boolean ProtectControlGroups;
	private @Getter @Setter String ProtectHome;
	private @Getter @Setter Boolean ProtectHostname;
	private @Getter @Setter Boolean ProtectKernelLogs;
	private @Getter @Setter Boolean ProtectKernelModules;
	private @Getter @Setter String ProtectSystem;
	private @Getter CopyOnWriteArrayList<String> ReadWritePaths = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String RestartSec;
	private @Getter @Setter CopyOnWriteArrayList<RestrictAddressFamiliesType> RestrictAddressFamilies = new CopyOnWriteArrayList<>();
	private @Getter @Setter String RestrictNamespaces;
	private @Getter @Setter Boolean RestrictRealtime;
	private @Getter @Setter Boolean RestrictSUIDSGID;
	private @Getter @Setter String RuntimeDirectory;
	private @Getter @Setter Boolean RuntimeDirectoryPreserve;
	private @Getter @Setter String StateDirectory;
	private @Getter @Setter String SystemCallArchitectures;
	private @Getter @Setter String SystemCallErrorNumber;
	private @Getter CopyOnWriteArrayList<String> SystemCallFilter = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String WatchdogSec;
	private @Getter @Setter String LimitNOFILE;
	private @Getter @Setter Boolean RemainAfterExit;
	private @Getter @Setter String ConditionVirtualization;
	private @Getter CopyOnWriteArrayList<String> ConditionDirectoryNotEmpty = new CopyOnWriteArrayList<>();// ?
	private @Getter @Setter String SuccessExitStatus;
	private @Getter @Setter String ConditionPathIsSymbolicLink;
	private @Getter @Setter String What;
	private @Getter @Setter String Where;
	private @Getter @Setter String Options;
	private @Getter @Setter String ConditionFileNotEmpty;
	private @Getter CopyOnWriteArrayList<String> ConditionNeedsUpdate = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String TimeoutSec;
	private @Getter @Setter Boolean ConditionFirstBoot;
	private @Getter @Setter String StandardOutput;
	private @Getter @Setter String StandardInput;
	private @Getter @Setter String StandardError;
	private @Getter CopyOnWriteArrayList<String> LoadCredential = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> RequiresMountsFor = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> Sockets = new CopyOnWriteArrayList<>();// OL
	private @Getter @Setter String ConditionPathIsMountPoint;
	private @Getter CopyOnWriteArrayList<String> ConditionKernelCommandLine = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Boolean RefuseManualStop;
	private @Getter @Setter KillModeType KillMode;
	private @Getter @Setter String TasksMax;
	private @Getter @Setter Boolean PrivateMounts;
	private @Getter @Setter Boolean StopWhenUnneeded;
	private @Getter @Setter String ConditionFileIsExecutable;
	private @Getter @Setter String Alias;
	private @Getter @Setter Long RestartPreventExitStatus;
	private @Getter CopyOnWriteArrayList<String> Environment = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<String> EnvironmentFile = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Long OnActiveSec;
	private @Getter CopyOnWriteArrayList<String> ExecStartPre = new CopyOnWriteArrayList<>();// ML
	private @Getter CopyOnWriteArrayList<SystemdElement> Also = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter Boolean ConditionACPower;
	private @Getter @Setter String User;
	private @Getter @Setter String SocketUser;
	private @Getter @Setter Integer Nice;
	private @Getter @Setter IOSchedulingClassType IOSchedulingClass;
	private @Getter @Setter Integer IOSchedulingPriority;
	private @Getter @Setter NotifyAccessType NotifyAccess;
	private @Getter @Setter Boolean PrivateNetwork;
	private @Getter @Setter Boolean PrivateDevices;
	private @Getter @Setter Boolean PrivateUsers;
	private @Getter @Setter Boolean ProtectKernelTunables;
	private @Getter @Setter String CacheDirectory;
	private @Getter @Setter String ReadOnlyPaths;
	private @Getter @Setter Boolean RemoveIPC;
	private @Getter @Setter String UMask;
	private @Getter @Setter String TimeoutStopSec;
	private @Getter @Setter String Group;
	private @Getter @Setter CAPs AmbientCapabilities;
	private @Getter @Setter Boolean DynamicUser;
	private @Getter @Setter SystemdElement OnFailure;
	private @Getter @Setter String WorkingDirectory;
	private @Deprecated @Getter @Setter String CPUSchedulingPolicy;// type
	private @Getter @Setter String SyslogIdentifier;
	private @Getter CopyOnWriteArrayList<String> SupplementaryGroups = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String LimitMEMLOCK;
	private @Getter @Setter String PathExists;
	private @Getter @Setter Boolean IgnoreSIGPIPE;
	private @Getter @Setter String FailureAction;
	private @Getter @Setter Integer CPUSchedulingPriority;
	private @Getter @Setter Boolean PermissionsStartOnly;
	private @Getter @Setter String TimeoutStartSec;
	private @Getter @Setter String RuntimeDirectoryMode;
	private @Getter @Setter String ConfigurationDirectory;
	private @Getter @Setter String ConfigurationDirectoryMode;
	private @Getter @Setter String StateDirectoryMode;
	private @Getter CopyOnWriteArrayList<String> ConditionPathExistsGlob = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String SocketGroup;
	private @Getter @Setter Integer LimitRTPRIO;
	private @Deprecated @Getter @Setter String FileDescriptorName;// type
	private @Getter @Setter String ExecStartPost;
	private @Getter @Setter KillSignalType KillSignal;
	private @Getter @Setter Boolean SendSIGKILL;
	private @Getter @Setter String ExecStopPost;
	private @Getter @Setter String BindsTo;
	private @Getter @Setter Boolean RefuseManualStart;
	private @Getter @Setter SystemdElement Unit;
	private @Getter @Setter Integer StartLimitIntervalSec;
	private @Getter @Setter Integer StartLimitBurst;
	private @Getter @Setter String Delegate;
	private @Getter @Setter String LimitNPROC;
	private @Getter @Setter String LimitCORE;
	private @Getter @Setter DevicePolicyType DevicePolicy;
	private @Getter @Setter Boolean CPUAccounting;
	private @Getter @Setter Boolean MemoryAccounting;
	private @Getter CopyOnWriteArrayList<SystemdElement> RequiredBy = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter SuccessActionType SuccessAction;
	private @Getter @Setter RestartKillSignalType RestartKillSignal;
	private @Getter @Setter Boolean AllowIsolate;
	private @Getter @Setter Boolean BindIPv6Only;
	private @Getter @Setter String AssertPathExists;
	private @Getter @Setter String LogsDirectory;
	private @Getter @Setter String LogsDirectoryMode;
	private @Getter @Setter SLICE Slice;
	private @Getter @Setter String UtmpIdentifier;
	private @Getter @Setter SystemdElement PartOf;
	private @Getter CopyOnWriteArrayList<String> ReadWriteDirectories = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String ReadOnlyDirectories;
	private @Getter @Setter Boolean PassPacketInfo;
	private @Getter @Setter String TTYPath;
	private @Getter @Setter OnFailureJobModeType OnFailureJobMode;
	private @Getter @Setter String RuntimeMaxSec;
	private @Getter @Setter Boolean NonBlocking;
	private @Getter @Setter String PAMName;
	private @Getter @Setter String ConditionPathIsDirectory;
	private @Getter @Setter Boolean SendSIGHUP;
	private @Getter CopyOnWriteArrayList<String> ConditionControlGroupController = new CopyOnWriteArrayList<>();// ML
	private @Getter @Setter String JobTimeoutSec;
	private @Getter @Setter JobTimeoutActionType JobTimeoutAction;
	private @Getter @Setter KeyringModeType KeyringMode;
	private @Getter @Setter String ListenSpecial;
	private @Getter @Setter Boolean TTYReset;
	private @Getter @Setter Boolean TTYVHangup;
	private @Getter @Setter Boolean TTYVTDisallocate;
	private @Getter @Setter String MemoryMin;
	private @Getter @Setter String MemoryLow;
	private @Getter CopyOnWriteArrayList<String> UnsetEnvironment = new CopyOnWriteArrayList<>();// OL
	private @Getter @Setter Boolean Writable;
	private @Getter @Setter Integer RestartForceExitStatus;
	private @Getter @Setter String DefaultInstance;
	private @Getter @Setter String StartLimitInterval;
	private @Getter @Setter String ProcSubset;

	public SystemdElement fileName(String name) throws Exception {
		elementType = ServiceUtils.getElementType(name);
		if (elementType == null) {
			throw new IllegalArgumentException(String.format("ошибка в расширении файла %s", name));
		}
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

	public PathSection getPathSection() {
		if (pathSection == null) {
			pathSection = new PathSection();
		}
		return pathSection;
	}

	public ServiceSection getServiceSection() {
		if (serviceSection == null) {
			serviceSection = new ServiceSection();
		}
		return serviceSection;
	}

	public SliceSection getSliceSection() {
		if (sliceSection == null) {
			sliceSection = new SliceSection();
		}
		return sliceSection;
	}

	public SocketSection getSocketSection() {
		if (socketSection == null) {
			socketSection = new SocketSection();
		}
		return socketSection;
	}

	public TimerSection getTimerSection() {
		if (timerSection == null) {
			timerSection = new TimerSection();
		}
		return timerSection;
	}

	public UnitSection getUnitSection() {
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

		return builder.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
