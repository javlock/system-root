package com.github.javlock.system.systemd.data.sections.impl;

import com.github.javlock.system.systemd.data.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class UnitSection extends Section {

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Unit;
	}

}
