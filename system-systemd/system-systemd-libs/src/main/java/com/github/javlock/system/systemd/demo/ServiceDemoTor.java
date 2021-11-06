package com.github.javlock.system.systemd.demo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.exceptions.AlreadyExistsException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.service.Service;
import com.github.javlock.system.systemd.data.service.sections.impl.InstallSection;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.CAPs;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.KillSignalType;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.NotifyAccessType;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.RestartType;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.ServiceSectionType;
import com.github.javlock.system.systemd.data.service.sections.impl.ServiceSection.YesNoType;
import com.github.javlock.system.systemd.data.service.sections.impl.UnitSection;

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
			unitSection.setDescription("Anonymizing overlay network for TCP");

			unitSection.appendAfter(new SystemdElement().fileName("syslog.target"));
			unitSection.appendAfter(new SystemdElement().fileName("network.target"));

			unitSection.appendAfter(new SystemdElement().fileName("nss-lookup.target"));

			// SERVICE
			serviceSection.setType(ServiceSectionType.notify);
			serviceSection.setNotifyAccess(NotifyAccessType.all);

			serviceSection.getExecStartPre().add("/usr/bin/tor -f /etc/tor/torrc --verify-config");
			serviceSection.getExecStart().add("/usr/bin/tor -f /etc/tor/torrc");
			serviceSection.getExecReload().add("/bin/kill -HUP ${MAINPID}");

			serviceSection.setKillSignal(KillSignalType.SIGINT);
			serviceSection.setTimeoutSec(60L);
			serviceSection.setRestartType(RestartType.on_failure);
			serviceSection.setWatchdogSec("1m");
			serviceSection.setLimitNOFILE(32768L);

			serviceSection.setPrivateTmp(YesNoType.yes);
			serviceSection.setPrivateDevices(YesNoType.yes);
			serviceSection.setProtectHome(YesNoType.yes);
			serviceSection.setProtectSystem("full");
			serviceSection.setReadOnlyDirectories("/");
			serviceSection.getReadWriteDirectories().add("-/var/lib/tor");
			serviceSection.getReadWriteDirectories().add("-/var/log/tor");
			serviceSection.setNoNewPrivileges(YesNoType.yes);

			CAPs[] caps = new CAPs[] { CAPs.CAP_SETUID, CAPs.CAP_SETGID, CAPs.CAP_NET_BIND_SERVICE,
					CAPs.CAP_DAC_READ_SEARCH };
			serviceSection.getCapabilityBoundingSet().addAll(Arrays.asList(caps));
			// INSTALL
			installSection.setWantedBy(new SystemdElement().fileName("multi-user.target"));

			LOGGER.info("\n{}", torService.toServiceFile());
		} catch (IllegalArgumentException | AlreadyExistsException e) {
			e.printStackTrace();
		}

	}

}
