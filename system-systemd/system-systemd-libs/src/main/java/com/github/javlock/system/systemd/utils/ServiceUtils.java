package com.github.javlock.system.systemd.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.SystemdElement.ELEMENTTYPE;

public class ServiceUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger("ServiceUtils");

	public static ELEMENTTYPE getElementType(String name) throws IllegalArgumentException {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("You fileName.isEmpty");
		}
		ELEMENTTYPE elementType = null;
		String[] typeAr = name.split("\\.");
		if (typeAr.length > 1) {
			String type = typeAr[typeAr.length - 1];
			elementType = ELEMENTTYPE.valueOf(type.toUpperCase().trim());
		}
		if (elementType == null) {
			String msg = String.format("Не получен elementType для файла с именем %s", name);
			LOGGER.error(msg);
			throw new IllegalArgumentException(msg);
		}
		return elementType;
	}

	public static SystemdElement parseFile(File serviceFile) throws Exception {
		return new SystemdParser().file(serviceFile).parse(serviceFile);
	}

}
