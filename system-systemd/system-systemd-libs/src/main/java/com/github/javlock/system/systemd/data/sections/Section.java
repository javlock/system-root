package com.github.javlock.system.systemd.data.sections;

import java.io.File;

import javax.naming.OperationNotSupportedException;

import com.github.javlock.system.apidata.exceptions.AlreadyExistsException;
import com.github.javlock.system.systemd.data.SystemdElement;

public class Section extends SystemdElement {
	public static Section getSectionFor(File currentFile, SystemdElement element, SECTIONNAME sectionName) {
		if (sectionName.equals(SECTIONNAME.UNIT)) {
			return element.getOrCreateUnitSection();
		} else if (sectionName.equals(SECTIONNAME.SERVICE)) {
			return element.getOrCreateServiceSection();
		} else if (sectionName.equals(SECTIONNAME.INSTALL)) {
			return element.getInstallSection();
		} else if (sectionName.equals(SECTIONNAME.PATH)) {
			return element.getOrCreatePathSection();
		} else if (sectionName.equals(SECTIONNAME.SOCKET)) {
			return element.getOrCreateSocketSection();
		} else if (sectionName.equals(SECTIONNAME.TIMER)) {
			return element.getOrCreateTimerSection();
		} else if (sectionName.equals(SECTIONNAME.AUTOMOUNT)) {
			return element.getAutomountSection();
		} else if (sectionName.equals(SECTIONNAME.MOUNT)) {
			return element.getMountSection();
		} else if (sectionName.equals(SECTIONNAME.SLISE)) {
			return element.getOrCreateSliceSection();
		}

		else {
			throw new IllegalArgumentException(String.format("Не получена секция %s для файла [%s] типа %s",
					sectionName.name(), currentFile, element.getClass().getSimpleName()));
		}
	}

	public static String retNameFor(SECTIONNAME sectionName) {
		return "[" + sectionName + "]";
	}

	public void appendAfter(SystemdElement element) throws AlreadyExistsException, OperationNotSupportedException {
		/*
		 * if (!getAfter().contains(element)) { getAfter().add(element); } else {
		 *
		 * throw new AlreadyExistsException(String.format("after contains %s",
		 * element)); }
		 */
		throw new AlreadyExistsException("TODO appendAfter");
	}

}
