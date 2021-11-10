package com.github.javlock.system.systemd.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.sections.impl.UnitSection;
import com.github.javlock.system.systemd.data.service.Service;

public class ServiceDemoKernel {
	private static final Logger LOGGER = LoggerFactory.getLogger("ServiceDemo");

	public static void main(String[] args) {
		try {
			Service kernelService = new Service();

			UnitSection unitSection = kernelService.getOrCreateUnitSection();
			ServiceSection serviceSection = kernelService.getServiceSection();
			InstallSection installSection = kernelService.getInstallSection();

			// UNIT
			unitSection.appendAfter(new SystemdElement().fileName("syslog.target"));
			unitSection.appendAfter(new SystemdElement().fileName("network.target"));
			unitSection.appendAfter(new SystemdElement().fileName("nss-lookup.target"));

			// SERVICE
			// INSTALL

			LOGGER.info("\n{}", kernelService.toServiceFile());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
