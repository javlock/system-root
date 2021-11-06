package com.github.javlock.system.systemd.data;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

public class SystemdElement {
	public enum ELEMENTTYPE {
		SERVICE, TARGET
	}

	private static final Logger LOGGER = LoggerFactory.getLogger("SystemdElement");

	private @Getter String fileName;
	private @Getter @Setter ELEMENTTYPE elementType;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SystemdElement other = (SystemdElement) obj;
		return elementType == other.elementType && Objects.equals(fileName, other.fileName);
	}

	public SystemdElement fileName(String name) throws IllegalArgumentException {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("You fileName.isEmpty");
		}
		try {
			String[] typeAr = name.split("\\.");
			if (typeAr.length > 1) {
				String type = typeAr[typeAr.length - 1];
				elementType = ELEMENTTYPE.valueOf(type.toUpperCase());
			}
		} catch (Exception e) {
			LOGGER.error("error get type for {} StackTrace:{}", name, e);
		}
		fileName = name;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(elementType, fileName);
	}

	public String toServiceFile() {
		return null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
