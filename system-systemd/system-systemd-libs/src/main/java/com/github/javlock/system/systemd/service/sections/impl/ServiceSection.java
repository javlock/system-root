package com.github.javlock.system.systemd.service.sections.impl;

import com.github.javlock.system.systemd.service.sections.Section;

public class ServiceSection extends Section {
	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Service;
	}

	@Override
	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');

		return builder.toString();
	}

}
