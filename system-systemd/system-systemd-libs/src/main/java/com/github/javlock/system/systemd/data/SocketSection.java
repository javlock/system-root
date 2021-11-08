package com.github.javlock.system.systemd.data;

import com.github.javlock.system.systemd.data.sections.Section;

public class SocketSection extends Section {
	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Socket;
	}

}
