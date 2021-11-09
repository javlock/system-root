package com.github.javlock.system.apidata;

public class DataSets {

	public static class Chars {
		public static class PrefixSuffix {
			public static final String GETTER_PREFIX = "get".toUpperCase();
			public static final String SETTER_PREFIX = "set".toUpperCase();

		}

		/**
		 * ALPHA_CAPS
		 */
		public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		/**
		 * ALPHA
		 */
		public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

		/**
		 * NUMERIC
		 */
		public static final String NUMERIC = "0123456789";
		/**
		 * SPECIAL_CHARS
		 */
		public static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

	}

	public enum VERSIONTYPE {
		MAIN, DEV
	}

}
