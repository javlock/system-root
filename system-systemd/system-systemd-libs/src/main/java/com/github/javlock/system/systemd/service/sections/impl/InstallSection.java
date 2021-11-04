package com.github.javlock.system.systemd.service.sections.impl;

import com.github.javlock.system.systemd.SystemdElement;
import com.github.javlock.system.systemd.service.sections.Section;

import lombok.Getter;
import lombok.Setter;

public class InstallSection extends Section {

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
