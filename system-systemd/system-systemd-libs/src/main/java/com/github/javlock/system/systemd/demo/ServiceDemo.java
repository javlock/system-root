package com.github.javlock.system.systemd.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.SystemdElement;
import com.github.javlock.system.systemd.service.Service;
import com.github.javlock.system.systemd.service.sections.impl.InstallSection;
import com.github.javlock.system.systemd.service.sections.impl.UnitSection;

public class ServiceDemo {
	private static final Logger LOGGER = LoggerFactory.getLogger("ServiceDemo");

	public static void main(String[] args) {
		LOGGER.info("------------------------------------------------------------------------");
		Service emptyService = new Service();
		LOGGER.info("\n{}", emptyService.toServiceFile());
		LOGGER.info("------------------------------------------------------------------------");

		Service torService = new Service();

		UnitSection unitSection = torService.getUnitSection();
		InstallSection installSection = torService.getInstallSection();

		unitSection.setDescription("Anonymizing overlay network for TCP");

		unitSection.getAfter().add(new SystemdElement().fileName("syslog.target"));
		unitSection.getAfter().add(new SystemdElement().fileName("network.target"));
		unitSection.getAfter().add(new SystemdElement().fileName("nss-lookup.target"));

		installSection.setWantedBy(new SystemdElement().fileName("multi-user.target"));

		LOGGER.info("\n{}", torService.toServiceFile());

	}

}
