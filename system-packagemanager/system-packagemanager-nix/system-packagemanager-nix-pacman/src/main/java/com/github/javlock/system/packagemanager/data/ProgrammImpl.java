package com.github.javlock.system.packagemanager.data;

import lombok.Getter;
import lombok.Setter;

public abstract class ProgrammImpl implements PackageInterface {
	private @Getter @Setter String name;
}
