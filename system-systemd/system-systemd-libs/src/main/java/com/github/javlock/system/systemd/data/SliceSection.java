package com.github.javlock.system.systemd.data;

import com.github.javlock.system.systemd.data.sections.Section;

public class SliceSection extends Section {
	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Slice;
	}

}
