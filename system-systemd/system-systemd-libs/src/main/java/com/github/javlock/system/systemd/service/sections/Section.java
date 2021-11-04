package com.github.javlock.system.systemd.service.sections;

import com.github.javlock.system.systemd.SystemdElement;

import lombok.Getter;
import lombok.Setter;

public class Section extends SystemdElement {

	public enum SECTIONNAME {
		NotConfigured, Unit, Service, Install
	}

	private @Getter @Setter SECTIONNAME name = SECTIONNAME.NotConfigured;

	public String retName() {
		return "[" + getName() + "]";
	}
}
