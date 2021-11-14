package com.github.javlock.system.systemd.data.sections.impl;

import com.github.javlock.system.systemd.data.sections.Section;

public class MOUNTSection extends Section {
	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.MOUNT;
	}
}
