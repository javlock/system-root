package com.github.javlock.system.systemd.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.javlock.system.systemd.data.sections.Section.SECTIONNAME;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection.YesNoType;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.utils.ServiceUtils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

@SuppressFBWarnings(value = "EI_EXPOSE_REP2")
public class SystemdElement {
	public enum ELEMENTTYPE {
		SERVICE, TARGET, TIMER, SOCKET, PATH, SLICE
	}

	public enum IPAddressDenyType {
		any
	}

	public enum ProtectProcType {
		invisible
	}

	public enum RestrictAddressFamilies {
		AF_UNIX, AF_NETLINK
	}

	public enum SystemCallArchitecturesType {
		NATIVE// to l case
	}

	private @Getter @Setter SystemCallArchitecturesType SystemCallArchitectures;
	private @Getter @Setter ProtectProcType protectProcType;

	private @Getter @Setter IPAddressDenyType iAddressDenyType;
	private @Getter @Setter UnitSection unitSection;
	private @Getter @Setter ServiceSection serviceSection;

	private @Getter @Setter InstallSection installSection;
	private @Getter List<SystemdElement> after = new ArrayList<>();
	private @Getter List<String> DeviceAllow = new ArrayList<>();

	private @Getter @Setter SECTIONNAME name = SECTIONNAME.NotConfigured;
	private @Getter @Setter String documentation;
	private @Getter @Setter SystemdElement requires;
	private @Getter @Setter SystemdElement conflicts;
	private @Getter @Setter String description;
	private @Getter String fileName;

	private @Getter @Setter ELEMENTTYPE elementType;
	private @Getter @Setter SystemdElement wantedBy;
	private @Getter @Setter String BusName;
	private @Getter @Setter Long FileDescriptorStoreMax;
	private @Getter @Setter YesNoType LockPersonality;
	private @Getter @Setter YesNoType MemoryDenyWriteExecute;
	private @Getter @Setter YesNoType ProtectClock;
	private @Getter @Setter YesNoType ProtectControlGroups;
	private @Getter @Setter YesNoType ProtectHostname;
	private @Getter @Setter YesNoType ProtectKernelLogs;

	private @Getter @Setter YesNoType ProtectKernelModules;
	private @Getter @Setter CopyOnWriteArrayList<String> ReadWritePaths = new CopyOnWriteArrayList<>();

	private @Getter @Setter Long RestartSec;
	private @Getter @Setter CopyOnWriteArrayList<RestrictAddressFamilies> RestrictAddressFamiliesType = new CopyOnWriteArrayList<>();

	private @Getter @Setter YesNoType RestrictNamespaces;
	private @Getter @Setter YesNoType RestrictRealtime;
	private @Getter @Setter YesNoType RestrictSUIDSGID;

	private @Getter @Setter CopyOnWriteArrayList<String> RuntimeDirectory = new CopyOnWriteArrayList<>();

	private @Getter @Setter YesNoType RuntimeDirectoryPreserve;
	private @Getter @Setter String StateDirectory;

	public SystemdElement fileName(String name) throws IllegalArgumentException {
		elementType = ServiceUtils.getElementType(name);
		fileName = name;
		return this;
	}

	private String printAfter() {
		StringBuilder builder = new StringBuilder();
		Object[] afterArray = getAfter().toArray();
		for (int i = 0; i < afterArray.length; i++) {
			if (afterArray[i]instanceof SystemdElement el) {
				builder.append(el.getFileName());
				if (i <= afterArray.length) {
					builder.append(" ");
				}
			}

		}
		return builder.toString();
	}

	public String retName() {
		return "[" + getName() + "]";
	}

	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');
		if (getDescription() != null) {
			builder.append("Description=").append(getDescription()).append('\n');
		}
		if (getAfter() != null) {
			builder.append("After=").append(printAfter()).append('\n');
		}

		if (getDocumentation() != null) {
			builder.append("Documentation=").append(getDocumentation()).append('\n');
		}
		if (getRequires() != null) {
			builder.append("Requires=").append(getRequires().getFileName()).append('\n');
		}
		if (getConflicts() != null) {
			builder.append("Conflicts=").append(getConflicts().getFileName()).append('\n');
		}
		if (wantedBy != null) {
			builder.append("WantedBy=").append(wantedBy.getFileName()).append('\n');
		}

		if (BusName != null) {
			builder.append("BusName=").append(BusName).append('\n');
		}
		if (FileDescriptorStoreMax != null) {
			builder.append("FileDescriptorStoreMax=").append(FileDescriptorStoreMax).append('\n');
		}

		if (iAddressDenyType != null) {
			builder.append("IPAddressDeny=").append(iAddressDenyType).append('\n');
		}
		if (LockPersonality != null) {
			builder.append("LockPersonality=").append(LockPersonality).append('\n');
		}
		if (MemoryDenyWriteExecute != null) {
			builder.append("MemoryDenyWriteExecute=").append(MemoryDenyWriteExecute).append('\n');
		}
		if (protectProcType != null) {
			builder.append("ProtectProc=").append(protectProcType).append('\n');
		}
		if (ProtectClock != null) {
			builder.append("ProtectClock=").append(ProtectClock).append('\n');
		}
		if (ProtectControlGroups != null) {
			builder.append("ProtectControlGroups=").append(ProtectControlGroups).append('\n');
		}
		if (ProtectHostname != null) {
			builder.append("ProtectHostname=").append(ProtectHostname).append('\n');
		}
		if (ProtectKernelLogs != null) {
			builder.append("ProtectKernelLogs=").append(ProtectKernelLogs).append('\n');
		}
		if (ProtectKernelModules != null) {
			builder.append("ProtectKernelModules=").append(ProtectKernelModules).append('\n');
		}
		if (!ReadWritePaths.isEmpty()) {
			builder.append("ReadWritePaths=");
			for (String path : ReadWritePaths) {
				builder.append(path).append(' ');
			}
			builder.append('\n');
		}
		if (RestartSec != null) {
			builder.append("RestartSec=").append(RestartSec).append('\n');
		}

		if (!RestrictAddressFamiliesType.isEmpty()) {
			builder.append("RestrictAddressFamilies=");
			for (RestrictAddressFamilies families : RestrictAddressFamiliesType) {
				builder.append(families.name()).append(' ');
			}
			builder.append('\n');
		}
		if (RestrictNamespaces != null) {
			builder.append("RestrictNamespaces=").append(RestrictNamespaces).append('\n');
		}
		if (RestrictRealtime != null) {
			builder.append("RestrictRealtime=").append(RestrictRealtime).append('\n');
		}
		if (RestrictSUIDSGID != null) {
			builder.append("RestrictSUIDSGID=").append(RestrictSUIDSGID).append('\n');
		}

		if (!RuntimeDirectory.isEmpty()) {
			builder.append("RuntimeDirectory=");
			for (String directory : RuntimeDirectory) {
				builder.append(directory).append(' ');
			}
			builder.append('\n');
		}
		if (RuntimeDirectoryPreserve != null) {
			builder.append("RuntimeDirectoryPreserve=").append(RuntimeDirectoryPreserve).append('\n');
		}

		if (StateDirectory != null) {
			builder.append("StateDirectory=").append(StateDirectory).append('\n');
		}
		if (SystemCallArchitectures != null) {
			builder.append("SystemCallArchitectures=").append(SystemCallArchitectures).append('\n');
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
