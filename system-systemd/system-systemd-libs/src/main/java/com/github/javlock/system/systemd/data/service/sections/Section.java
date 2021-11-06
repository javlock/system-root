package com.github.javlock.system.systemd.data.service.sections;

import com.github.javlock.system.systemd.data.SystemdElement;

import lombok.Getter;
import lombok.Setter;

//TODO Override the "equals" method in this class.
public class Section extends SystemdElement {

	public enum SECTIONNAME {
		NotConfigured, Unit, Service, Install
	}

	private @Getter @Setter SECTIONNAME name = SECTIONNAME.NotConfigured;

	public String retName() {
		return "[" + getName() + "]";
	}
}
