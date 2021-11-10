package com.github.javlock.system.systemd.utils;

import java.io.File;
import java.lang.reflect.Field;

import com.github.javlock.system.apidata.exceptions.ObjectTypeException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.SystemdElement.ELEMENTTYPE;

public class ServiceUtils {
	public static boolean containsKeyInSystemdElement(Class<SystemdElement> class1, String key) {
		String realKeyUpperCase = key.toUpperCase();
		Field[] systemdElementClassFields = class1.getDeclaredFields();
		for (Field field : systemdElementClassFields) {
			String fieldNameUpperCase = field.getName().toUpperCase();
			if (fieldNameUpperCase.equals(realKeyUpperCase)) {
				return true;
			}
		}
		return false;
	}

	public static ELEMENTTYPE getElementType(String name) throws IllegalArgumentException, ObjectTypeException {
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
			String msg = String.format("Не получен elementType для файла (ошибка в расширении файла) с именем %s",
					name);
			throw new ObjectTypeException(msg);
		}
		return elementType;
	}

	public static SystemdElement parseFile(File serviceFile) throws Exception {
		return new SystemdParser().file(serviceFile).parse(serviceFile);
	}

	private ServiceUtils() {
	}

}
