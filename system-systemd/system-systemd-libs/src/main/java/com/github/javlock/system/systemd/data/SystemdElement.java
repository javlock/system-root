package com.github.javlock.system.systemd.data;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.javlock.system.apidata.exceptions.ObjectTypeException;
import com.github.javlock.system.systemd.data.sections.impl.AUTOMOUNTSection;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.MOUNTSection;
import com.github.javlock.system.systemd.data.sections.impl.PathSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.SliceSection;
import com.github.javlock.system.systemd.data.sections.impl.SocketSection;
import com.github.javlock.system.systemd.data.sections.impl.TimerSection;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.utils.ServiceUtils;

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
		inherit
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

	public enum TypeType {
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

	// field files
	private @Getter @Setter Boolean allowIsolate;
	private @Getter @Setter Boolean bindIPv6Only;
	private @Getter @Setter Boolean defaultDependencies;
	private @Getter @Setter Boolean dynamicUser;
	private @Getter @Setter Boolean ignoreOnIsolate;
	private @Getter @Setter Boolean lockPersonality;
	private @Getter @Setter Boolean memoryDenyWriteExecute;
	private @Getter @Setter Boolean nonBlocking;
	private @Getter @Setter Boolean noNewPrivileges;
	private @Getter @Setter Boolean privateDevices;
	private @Getter @Setter Boolean privateNetwork;
	private @Getter @Setter Boolean privateTmp;
	private @Getter @Setter Boolean protectControlGroups;
	private @Getter @Setter Boolean protectHome;
	private @Getter @Setter Boolean protectHostname;
	private @Getter @Setter Boolean protectKernelModules;
	private @Getter @Setter Boolean refuseManualStart;
	private @Getter @Setter Boolean refuseManualStop;
	private @Getter @Setter Boolean remainAfterExit;
	private @Getter @Setter Boolean removeOnStop;
	private @Getter @Setter Boolean restrictRealtime;
	private @Getter @Setter Boolean stopWhenUnneeded;
	private @Getter @Setter ConditionSecurityType conditionSecurity;
	private @Getter @Setter CopyOnWriteArrayList<CAPs> ambientCapabilities = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<CAPs> capabilityBoundingSet = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<CAPs> conditionCapability = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<RestrictAddressFamiliesType> restrictAddressFamilies = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> conditionPathExists = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> documentation = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> environmentFile = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> environment = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> execReload = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> execStart = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> execStartPre = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> listenStream = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<String> readWriteDirectories = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> after = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> also = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> before = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> conflicts = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> requiredBy = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> requires = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> service = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> wantedBy = new CopyOnWriteArrayList<>();
	private @Getter @Setter CopyOnWriteArrayList<SystemdElement> wants = new CopyOnWriteArrayList<>();
	private @Getter @Setter DevicePolicyType devicePolicy;
	private @Getter @Setter KillModeType killMode;
	private @Getter @Setter KillSignalType killSignal;
	private @Getter @Setter NotifyAccessType notifyAccess;
	private @Getter @Setter RestartType restart;
	private @Getter @Setter String assertPathExists;
	private @Getter @Setter String busName;
	private @Getter @Setter String conditionControlGroupController;
	private @Getter @Setter String conditionDirectoryNotEmpty;
	private @Getter @Setter String conditionNeedsUpdate;
	private @Getter @Setter String conditionPathIsReadWrite;
	private @Getter @Setter String conditionPathIsSymbolicLink;
	private @Getter @Setter String conditionVirtualization;
	private @Getter @Setter String delegate;
	private @Getter @Setter String description;
	private @Getter @Setter String deviceAllow;
	private @Getter @Setter String execStop;
	private @Getter @Setter String execStopPost;
	private @Getter @Setter String limitCORE;
	private @Getter @Setter String limitMEMLOCK;
	private @Getter @Setter String limitNOFILE;
	private @Getter @Setter String listenDatagram;
	private @Getter @Setter String onBootSec;
	private @Getter @Setter String oOMScoreAdjust;
	private @Getter @Setter String options;
	private @Getter @Setter String pAMName;
	private @Getter @Setter String pIDFile;
	private @Getter @Setter String protectSystem;
	private @Getter @Setter String readOnlyDirectories;
	private @Getter @Setter String runtimeDirectory;
	private @Getter @Setter String slice;
	private @Getter @Setter String socketMode;
	private @Getter @Setter String standardOutput;
	private @Getter @Setter String stateDirectory;
	private @Getter @Setter String tasksMax;
	private @Getter @Setter String timeoutSec;
	private @Getter @Setter String timeoutStartSec;
	private @Getter @Setter String timeoutStopSec;
	private @Getter @Setter String user;
	private @Getter @Setter String utmpIdentifier;
	private @Getter @Setter String watchdogSec;
	private @Getter @Setter String what;
	private @Getter @Setter String where;

	private @Getter @Setter String requiresMountsFor;
	private @Getter @Setter String iPAddressDeny;
	private @Getter @Setter KeyringModeType keyringMode;
	private @Getter @Setter String receiveBuffer;
	private @Getter @Setter SuccessActionType successAction;

	private @Getter @Setter String workingDirectory;
	private @Getter @Setter SystemdElement bindsTo;
	private @Getter @Setter TypeType type;

	// field files

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

		return builder.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
