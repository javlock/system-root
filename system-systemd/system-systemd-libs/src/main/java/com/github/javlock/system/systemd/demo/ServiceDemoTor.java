package com.github.javlock.system.systemd.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.data.service.Service;

public class ServiceDemoTor {
	private static final Logger LOGGER = LoggerFactory.getLogger("ServiceDemo");

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			Service torService = new Service();

			UnitSection unitSection = torService.getUnitSection();
			ServiceSection serviceSection = torService.getServiceSection();
			InstallSection installSection = torService.getInstallSection();

			// UNIT

			unitSection.appendAfter(new SystemdElement().fileName("syslog.target"));
			unitSection.appendAfter(new SystemdElement().fileName("network.target"));
			unitSection.appendAfter(new SystemdElement().fileName("nss-lookup.target"));

			// SERVICE
			// INSTALL

			LOGGER.info("\n{}", torService.toServiceFile());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
