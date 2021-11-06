package com.github.javlock.system.systemd.data.sections;

import com.github.javlock.system.systemd.data.SystemdElement;

public class Section extends SystemdElement {

	public enum SECTIONNAME {
		NotConfigured, Unit, Service, Install, Timer, Socket, PATH
	}

	public static String retNameFor(SECTIONNAME sectionName) {
		return "[" + sectionName + "]";
	}

}
