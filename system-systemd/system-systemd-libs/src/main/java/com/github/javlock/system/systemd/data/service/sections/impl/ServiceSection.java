package com.github.javlock.system.systemd.data.service.sections.impl;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.javlock.system.systemd.data.service.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

@SuppressFBWarnings(value = "EI_EXPOSE_REP")
public class ServiceSection extends Section {

	public enum CAPs {
		CAP_SETUID, CAP_SETGID, CAP_NET_BIND_SERVICE, CAP_DAC_READ_SEARCH
	}

	public enum KillSignalType {
		SIGINT
	}

	public enum NotifyAccessType {
		none, main, exec, all
	}

	public enum RestartType {
		on_failure
	}

	public enum ServiceSectionType {
		notify
	}

	public enum YesNoType {
		yes, no
	}

	private @Getter @Setter ServiceSectionType type;

	private @Getter @Setter NotifyAccessType notifyAccess;

	private @Getter CopyOnWriteArrayList<String> execStartPre = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> execStart = new CopyOnWriteArrayList<>();

	private @Getter CopyOnWriteArrayList<String> execStartPost = new CopyOnWriteArrayList<>();

	private @Getter CopyOnWriteArrayList<String> execReload = new CopyOnWriteArrayList<>();
	private @Getter CopyOnWriteArrayList<String> execStop = new CopyOnWriteArrayList<>();

	private @Getter CopyOnWriteArrayList<String> execStopPost = new CopyOnWriteArrayList<>();

	private @Getter @Setter KillSignalType killSignal;
	private @Getter @Setter Long timeoutSec;
	private @Getter @Setter RestartType restartType;
	private @Getter @Setter String watchdogSec;

	private @Getter @Setter Long limitNOFILE;
	private @Getter @Setter YesNoType privateTmp;
	private @Getter @Setter YesNoType privateDevices;
	private @Getter @Setter YesNoType protectHome;
	private @Deprecated(forRemoval = true) @Getter @Setter String protectSystem;

	private @Deprecated(forRemoval = true) @Getter @Setter String readOnlyDirectories;
	private @Getter CopyOnWriteArrayList<String> readWriteDirectories = new CopyOnWriteArrayList<>();

	private @Getter @Setter YesNoType noNewPrivileges;
	private @Getter CopyOnWriteArrayList<CAPs> capabilityBoundingSet = new CopyOnWriteArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ServiceSection other = (ServiceSection) obj;
		return Objects.equals(capabilityBoundingSet, other.capabilityBoundingSet)
				&& Objects.equals(execReload, other.execReload) && Objects.equals(execStart, other.execStart)
				&& Objects.equals(execStartPost, other.execStartPost)
				&& Objects.equals(execStartPre, other.execStartPre) && Objects.equals(execStop, other.execStop)
				&& Objects.equals(execStopPost, other.execStopPost) && killSignal == other.killSignal
				&& Objects.equals(limitNOFILE, other.limitNOFILE) && noNewPrivileges == other.noNewPrivileges
				&& notifyAccess == other.notifyAccess && privateDevices == other.privateDevices
				&& privateTmp == other.privateTmp && protectHome == other.protectHome
				&& Objects.equals(protectSystem, other.protectSystem)
				&& Objects.equals(readOnlyDirectories, other.readOnlyDirectories)
				&& Objects.equals(readWriteDirectories, other.readWriteDirectories) && restartType == other.restartType
				&& Objects.equals(timeoutSec, other.timeoutSec) && type == other.type
				&& Objects.equals(watchdogSec, other.watchdogSec);
	}

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Service;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(capabilityBoundingSet, execReload, execStart, execStartPost,
				execStartPre, execStop, execStopPost, killSignal, limitNOFILE, noNewPrivileges, notifyAccess,
				privateDevices, privateTmp, protectHome, protectSystem, readOnlyDirectories, readWriteDirectories,
				restartType, timeoutSec, type, watchdogSec);
		return result;
	}

	@Override
	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');
		if (type != null) {
			builder.append("Type=").append(type).append('\n');
		}
		if (notifyAccess != null) {
			builder.append("NotifyAccess=").append(notifyAccess).append('\n');
		}

		// cmds
		// cmds-start
		for (String cmd : execStartPre) {
			builder.append("ExecStartPre=").append(cmd).append('\n');
		}
		for (String cmd : execStart) {
			builder.append("ExecStart=").append(cmd).append('\n');
		}
		for (String cmd : execStartPost) {
			builder.append("ExecStartPost=").append(cmd).append('\n');
		}

		// cmds-reload
		for (String cmd : execReload) {
			builder.append("ExecReload=").append(cmd).append('\n');
		}

		// cmds-stop
		for (String cmd : execStop) {
			builder.append("ExecStop=").append(cmd).append('\n');
		}
		for (String cmd : execStopPost) {
			builder.append("ExecStopPost=").append(cmd).append('\n');
		}

		//
		//
		if (killSignal != null) {
			builder.append("KillSignal=").append(killSignal).append('\n');
		}
		if (timeoutSec != null) {
			builder.append("TimeoutSec=").append(timeoutSec).append('\n');
		}
		if (restartType != null) {
			builder.append("Restart=").append(restartType.toString().replaceAll("_{1,}", "-")).append('\n');
		}
		if (watchdogSec != null) {
			builder.append("WatchdogSec=").append(watchdogSec).append('\n');
		}
		if (limitNOFILE != null) {
			builder.append("LimitNOFILE=").append(limitNOFILE).append('\n');
		}
		if (privateTmp != null) {
			builder.append("PrivateTmp=").append(privateTmp).append('\n');
		}
		if (privateDevices != null) {
			builder.append("PrivateDevices=").append(privateDevices).append('\n');
		}
		if (protectHome != null) {
			builder.append("ProtectHome=").append(protectHome).append('\n');
		}
		if (protectSystem != null) {
			builder.append("ProtectSystem=").append(protectSystem).append('\n');
		}
		if (readOnlyDirectories != null) {
			builder.append("ReadOnlyDirectories=").append(readOnlyDirectories).append('\n');
		}

		for (String dir : readWriteDirectories) {
			builder.append("ReadWriteDirectories=").append(dir).append('\n');
		}

		if (noNewPrivileges != null) {
			builder.append("NoNewPrivileges=").append(noNewPrivileges).append('\n');
		}

		if (!capabilityBoundingSet.isEmpty()) {
			builder.append("CapabilityBoundingSet=");
			for (CAPs capability : capabilityBoundingSet) {
				builder.append(capability).append(" ");
			}
		}

		return builder.toString();
	}

}
