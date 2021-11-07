package com.github.javlock.system.systemd.data.sections;

import java.io.File;

import com.github.javlock.system.systemd.data.SystemdElement;

public class Section extends SystemdElement {

	public static Section getSectionFor(File currentFile, SystemdElement element, SECTIONNAME sectionName) {
		if (sectionName.equals(SECTIONNAME.Unit)) {
			return element.getUnitSection();
		} else if (sectionName.equals(SECTIONNAME.Service)) {
			return element.getServiceSection();
		} else if (sectionName.equals(SECTIONNAME.Install)) {
			return element.getInstallSection();
		} else if (sectionName.equals(SECTIONNAME.PATH)) {
			return element.getPathSection();
		} else if (sectionName.equals(SECTIONNAME.Socket)) {
			return element.getSocketSection();
		} else if (sectionName.equals(SECTIONNAME.Timer)) {
			return element.getTimerSection();
		} else if (sectionName.equals(SECTIONNAME.AUTOMOUNT)) {
			return element.getAutomountSection();
		} else if (sectionName.equals(SECTIONNAME.Mount)) {
			return element.getMountSection();
		} else if (sectionName.equals(SECTIONNAME.Slice)) {
			return element.getSliceSection();
		}

		else {
			throw new IllegalArgumentException(String.format("Не получена секция %s для файла [%s] типа %s",
					sectionName.name(), currentFile, element.getClass().getSimpleName()));
		}
	}

	public static String retNameFor(SECTIONNAME sectionName) {
		return "[" + sectionName + "]";
	}

}
