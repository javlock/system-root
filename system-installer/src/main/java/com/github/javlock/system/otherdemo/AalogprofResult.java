package com.github.javlock.system.otherdemo;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

public class AalogprofResult {
	private @Getter @Setter File profileFile;
	private @Getter @Setter boolean owner;
	private @Getter @Setter String dest;
	private @Getter @Setter String newMode;

	public boolean check() {
		if (dest == null) {
			return false;
		}
		if (newMode == null) {
			return false;

		}

		return true;
	}

	public void clear() {
		this.profileFile = null;
		this.owner = false;
		this.dest = null;
		this.newMode = null;
	}

	protected String getDest(String line) {
		System.err.println(line);

		String regEx = "/[-\\.\\w\\d\\/\\ ]{1,}";
		final Pattern pattern = Pattern.compile(regEx, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(line);

		while (matcher.find()) {
			String ress = matcher.group(0);
			return ress.replaceAll("[\\ ]{1,}", "*");
		}
		return null;
	}

	private String getPath(String line) {
		String regEx = "/[-\\w\\d\\/]{1,}";
		final Pattern pattern = Pattern.compile(regEx, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(line);

		while (matcher.find()) {
			return matcher.group(0);
		}
		return null;
	}

	public void parseLine(String line) {
		if (line.toLowerCase().contains("Профиль".toLowerCase())) {

			String regex = "[\\/]{1,}";

			String path = getPath(line);
			path = path.replaceFirst(regex, "");

			String to = ".";
			File realFile = new File("/etc/apparmor.d", path.replaceAll(regex, to));
			setProfileFile(realFile);
			return;
		}
		if (line.toLowerCase().contains("Расположение".toLowerCase())) {
			setDest(getDest(line));
		}

		if (line.toLowerCase().contains("Новый режим".toLowerCase())) {
			owner = parseOwner(line);
			newMode = parseMode(line);
		}
	}

	private String parseMode(String line) {
		return line.replace("owner ", "").split(":")[1].trim();
	}

	private Boolean parseOwner(String line) {
		return line.contains("owner");
	}

	public String toRuleString() {
		StringBuilder builder = new StringBuilder();
		builder.append("  ");
		if (owner) {
			builder.append("owner ");
		}
		builder.append(dest).append(' ').append(newMode).append(',');

		return builder.toString();
	}

	@Override
	public String toString() {
		return "AalogprofResult [profileFile=" + profileFile + ", owner=" + owner + ", dest=" + dest + ", newMode="
				+ newMode + "]";
	}

}
