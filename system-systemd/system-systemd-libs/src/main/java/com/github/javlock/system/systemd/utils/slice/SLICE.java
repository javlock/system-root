package com.github.javlock.system.systemd.utils.slice;

import com.github.javlock.system.apidata.exceptions.ObjectTypeException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.utils.ServiceUtils;

public class SLICE extends SystemdElement {
	@Override
	public SLICE fileName(String name) throws IllegalArgumentException, ObjectTypeException {
		if (!ServiceUtils.getElementType(name).equals(ELEMENTTYPE.SLICE)) {
			throw new ObjectTypeException(
					String.format("Не удалось создать объект типа ELEMENTTYPE.SLICE для [%s]", name));
		}
		setElementType(ELEMENTTYPE.SLICE);
		setFileName(name);
		return this;
	}

}
