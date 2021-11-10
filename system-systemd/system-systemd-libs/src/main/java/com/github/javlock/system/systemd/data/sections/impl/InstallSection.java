package com.github.javlock.system.systemd.data.sections.impl;

import com.github.javlock.system.systemd.data.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "EI_EXPOSE_REP")
public class InstallSection extends Section {

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Install;
	}

}
