package com.github.javlock.system.systemd.data.service;

import com.github.javlock.system.systemd.data.SystemdElement;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class Service extends SystemdElement {

	@Override
	public String toServiceFile() {
		StringBuilder result = new StringBuilder();
		result.append(getUnitSection().toServiceFile()).append('\n');
		result.append(getServiceSection().toServiceFile()).append('\n');
		result.append(getInstallSection().toServiceFile()).append('\n');
		return result.toString();
	}

}
