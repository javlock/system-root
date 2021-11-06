package com.github.javlock.system.systemd.data.service.sections.impl;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.service.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

//TODO Override the "equals" method in this class.
@SuppressFBWarnings(value = "EI_EXPOSE_REP")
public class InstallSection extends Section {

	@SuppressFBWarnings(value = "EI_EXPOSE_REP2")
	private @Getter @Setter SystemdElement wantedBy;

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Install;
	}

	@Override
	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');
		if (wantedBy != null) {
			builder.append("Documentation=").append(wantedBy.getFileName()).append('\n');
		}

		return builder.toString();
	}
}
