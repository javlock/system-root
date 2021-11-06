package com.github.javlock.system.systemd.data.sections.impl;

import com.github.javlock.system.apidata.exceptions.AlreadyExistsException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class UnitSection extends Section {

	public void appendAfter(SystemdElement element) throws AlreadyExistsException {
		if (!getAfter().contains(element)) {
			getAfter().add(element);
		} else {
			throw new AlreadyExistsException(String.format("after contains %s", element));
		}
	}

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Unit;
	}

}
