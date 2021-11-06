package com.github.javlock.system.systemd.data.service;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.service.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.service.sections.impl.UnitSection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

//TODO Override the "equals" method in this class.
@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class Service extends SystemdElement {
	private @Getter UnitSection unitSection = new UnitSection();
	private @Getter ServiceSection serviceSection = new ServiceSection();
	private @Getter InstallSection installSection = new InstallSection();

	@Override
	public String toServiceFile() {
		StringBuilder result = new StringBuilder();
		result.append(unitSection.toServiceFile()).append('\n');
		result.append(serviceSection.toServiceFile()).append('\n');
		result.append(installSection.toServiceFile()).append('\n');
		return result.toString();
	}

}
