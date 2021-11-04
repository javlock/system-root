package com.github.javlock.system.systemd;

import lombok.Getter;

public class SystemdElement {

	public enum ELEMENTTYPE {
		SERVICE, TARGET
	}

	private @Getter String fileName;

	public SystemdElement fileName(String name) {
		// TODO распарсить имя, получить тип файла
		fileName = name;
		return this;
	}

	public String toServiceFile() {
		return null;
	}
}
