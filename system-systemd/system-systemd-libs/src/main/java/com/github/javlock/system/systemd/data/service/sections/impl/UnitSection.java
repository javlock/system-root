package com.github.javlock.system.systemd.data.service.sections.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.javlock.system.apidata.exceptions.AlreadyExistsException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.service.sections.Section;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

@SuppressFBWarnings(value = { "EI_EXPOSE_REP", "EI_EXPOSE_REP2" })
public class UnitSection extends Section {

	private @Getter @Setter String description;

	private List<SystemdElement> after = new ArrayList<>();

	private @Getter @Setter String documentation;
	private @Getter @Setter SystemdElement requires;

	private @Getter @Setter SystemdElement conflicts;

	public void appendAfter(SystemdElement element) throws AlreadyExistsException {
		if (!after.contains(element)) {
			after.add(element);
		} else {
			throw new AlreadyExistsException(String.format("after contains %s", element));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UnitSection other = (UnitSection) obj;
		return Objects.equals(after, other.after) && Objects.equals(conflicts, other.conflicts)
				&& Objects.equals(description, other.description) && Objects.equals(documentation, other.documentation)
				&& Objects.equals(requires, other.requires);
	}

	@Override
	public SECTIONNAME getName() {
		return SECTIONNAME.Unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(after, conflicts, description, documentation, requires);
		return result;
	}

	private String printAfter() {
		StringBuilder builder = new StringBuilder();
		Object[] afterArray = after.toArray();
		for (int i = 0; i < afterArray.length; i++) {
			if (afterArray[i]instanceof SystemdElement el) {
				builder.append(el.getFileName());
				if (i <= afterArray.length) {
					builder.append(" ");
				}
			}

		}
		return builder.toString();
	}

	@Override
	public String toServiceFile() {
		StringBuilder builder = new StringBuilder();
		builder.append(retName()).append('\n');
		if (description != null) {
			builder.append("Description=").append(description).append('\n');
		}
		if (after != null) {
			builder.append("After=").append(printAfter()).append('\n');
		}

		if (documentation != null) {
			builder.append("Documentation=").append(documentation).append('\n');
		}
		if (requires != null) {
			builder.append("Requires=").append(requires.getFileName()).append('\n');
		}
		if (conflicts != null) {
			builder.append("Conflicts=").append(conflicts.getFileName()).append('\n');
		}

		return builder.toString();
	}
}
