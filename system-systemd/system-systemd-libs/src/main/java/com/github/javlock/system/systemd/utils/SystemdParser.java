package com.github.javlock.system.systemd.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.DataSets;
import com.github.javlock.system.apidata.exceptions.ObjectTypeException;
import com.github.javlock.system.apidata.systemd.data.SystDKeyMeta;
import com.github.javlock.system.systemd.NotParsedException;
import com.github.javlock.system.systemd.data.SystemdElement;
import com.github.javlock.system.systemd.data.SystemdElement.CAPs;
import com.github.javlock.system.systemd.data.SystemdElement.ELEMENTTYPE;
import com.github.javlock.system.systemd.data.SystemdElement.RestrictAddressFamiliesType;
import com.github.javlock.system.systemd.data.SystemdElement.SECTIONNAME;
import com.github.javlock.system.systemd.data.automount.AUTOMOUNT;
import com.github.javlock.system.systemd.data.conf.CONF;
import com.github.javlock.system.systemd.data.mount.MOUNT;
import com.github.javlock.system.systemd.data.path.PATH;
import com.github.javlock.system.systemd.data.sections.Section;
import com.github.javlock.system.systemd.data.service.Service;
import com.github.javlock.system.systemd.data.slice.SLICE;
import com.github.javlock.system.systemd.data.socket.SOCKET;
import com.github.javlock.system.systemd.data.target.Target;
import com.github.javlock.system.systemd.data.timer.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "EI_EXPOSE_REP")
public class SystemdParser {
	public static interface SystemdParserKeyListener {

		boolean inputString(Section section, Field keyField, boolean keyPrivate, boolean keyProtected, String realKey,
				String value)
				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotParsedException;

	}

	public static interface SystemdParserListener {

		void errorStream(String msg) throws Exception;

		void input(String input) throws Exception;

		void withSection(Section section, String key, String value) throws Exception;

		void withSectionName(SECTIONNAME currentSectionName, String input) throws Exception;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger("SystemdParser");

	static SystemdParserKeyListener systemdParserKeyListener = new SystemdParserKeyListener() {

		@Override
		public boolean inputString(Section section, Field keyField, boolean keyPrivate, boolean keyProtected,
				String realKey, String value)
				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotParsedException {
			Class<?> systemdElementclaClass = keyField.getDeclaringClass();

			Optional<Method> checkNullByGetterOptional = getGetOrSetterByKey(
					systemdElementclaClass.getDeclaredMethods(), DataSets.Chars.PrefixSuffix.GETTER_PREFIX, realKey);
			boolean getterIsExist = checkNullByGetterOptional.isPresent();

			if (getterIsExist) {
				Method getter = checkNullByGetterOptional.get();
				Object retValue = getter.invoke(section);
				if (retValue != null) {
					throw new NotParsedException(String.format(
							"SystemdParserKeyListener.inputString.NoNull for section:[%s] key:[%s]", section, realKey));
				}

				Optional<Method> setterOptional = getGetOrSetterByKey(systemdElementclaClass.getDeclaredMethods(),
						DataSets.Chars.PrefixSuffix.SETTER_PREFIX, realKey);
				if (setterOptional.isPresent()) {
					Method setter = setterOptional.get();
					setter.invoke(section, value);
					return true;
				}
			} else {

				throw new NotParsedException(
						String.format("SystemdParserKeyListener.inputString.getterNOTExist for section:[%s] key:[%s]",
								section, realKey));
			}
			return false;
		}

	};

	private static Optional<Method> enumValueOfMethodByKeyType(Class<?> keyType) {
		for (Method method : keyType.getDeclaredMethods()) {
			if (method.getName().equals("valueOf")) {
				return Optional.of(method);
			}
		}
		return Optional.empty();
	}

	private static Optional<Method> getGetOrSetterByKey(Method[] methods, String prefix, String realKey) {
		String realKeyGetterName = prefix + realKey.toUpperCase();
		for (Method method : methods) {
			String methodNameUC = method.getName().toUpperCase();
			if (methodNameUC.equals(realKeyGetterName)) {
				return Optional.of(method);
			}
		}
		return Optional.empty();
	}

	private static Optional<Field> getKeyField(Class<SystemdElement> class1, String realKey) {
		String realKeyUC = realKey.toUpperCase();
		for (Field field : class1.getDeclaredFields()) {
			String fieldNameUC = field.getName().toUpperCase();
			if (fieldNameUC.equals(realKeyUC)) {
				return Optional.of(field);
			}
		}
		return Optional.empty();
	}

	private static ArrayList<Class<?>> getKeyParamsClasses(Field keyField) {
		ArrayList<Class<?>> classes = new ArrayList<>();
		if (keyField.getGenericType()instanceof ParameterizedType generTyp) {
			ParameterizedType pType = generTyp;
			Type[] arr = pType.getActualTypeArguments();
			for (Type tp : arr) {
				Class<?> clzz = (Class<?>) tp;
				classes.add(clzz);
			}
		}
		return classes;
	}

	private static boolean keyIsEnum(Class<?> keyType) {
		return keyType.isEnum();
	}

	public static Boolean parseYesNoBoolean(String value) throws IllegalArgumentException {
		boolean ret;
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on")) {
			ret = true;
		} else if (value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false")) {
			ret = false;
		} else {
			throw new IllegalArgumentException(value);
		}
		return ret;
	}

	public static boolean setValueViaReflection(Class<SystemdElement> class1, Section section, String realKey,
			String value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NotParsedException, ObjectTypeException {
		Optional<Field> keyFieldOptional = getKeyField(class1, realKey);

		if (!keyFieldOptional.isPresent()) {
			throw new NoSuchFieldException(
					String.format("Field:[%s] для value:[%s] нет в (SystemdElement.java:1)", realKey, value));
		}

		Field keyField = keyFieldOptional.get();

		int modifiers = keyField.getModifiers();
		boolean keyPrivate = Modifier.isPrivate(modifiers);
		boolean keyProtected = Modifier.isProtected(modifiers);

		Class<?> keyType = keyField.getType();

		boolean keyIsEnum = keyIsEnum(keyType);

		if (keyIsEnum) {
			Optional<Method> enumValueOfMethodOptional = enumValueOfMethodByKeyType(keyType);
			if (enumValueOfMethodOptional.isPresent()) {
				// ENUMCLASS ENUMCLASSType.valueOf(java.lang.String)
				Method enumValueOfMethod = enumValueOfMethodOptional.get();
				// ENUMCLASS.obj
				Object obj = enumValueOfMethod.invoke(null, value.replace("-", "_".toUpperCase()));
				if (keyPrivate) {
					try {
						keyField.setAccessible(true);
						keyField.set(section, obj);
						keyField.setAccessible(false);
					} catch (Exception e) {
						keyField.setAccessible(false);
						throw e;
					}
					return true;
				}

			} else {
				throw new NotParsedException(String.format("Не получен ValueOf для section[%s] key[%s] value[%s]",
						section.getName(), realKey, value));
			}
			return false;

		} else if (keyType == String.class) {
			return systemdParserKeyListener.inputString(section, keyField, keyPrivate, keyProtected, realKey, value);
		} else if (keyType == CopyOnWriteArrayList.class) {
			Class<?> aaa = keyField.getDeclaringClass();
			Optional<Method> getterOptional = getGetOrSetterByKey(aaa.getDeclaredMethods(),
					DataSets.Chars.PrefixSuffix.GETTER_PREFIX, realKey);
			if (getterOptional.isPresent()) {
				Method getter = getterOptional.get();
				CopyOnWriteArrayList<?> listData = (CopyOnWriteArrayList<?>) getter.invoke(section);
				ArrayList<Class<?>> listType = getKeyParamsClasses(keyField);
				if (listType.size() != 1) {
					throw new NotParsedException(
							String.format("Странная у вас) коллекция ) ** section:[%s] key:[%s] value:[%s] ",
									section.getClass().getSimpleName(), realKey, value));
				}
				Class<?> type = listType.get(0);
				if (type == SystemdElement.class) {
					@SuppressWarnings("unchecked")
					CopyOnWriteArrayList<SystemdElement> listDataSystemdElement = (CopyOnWriteArrayList<SystemdElement>) listData;

					String[] valueArr = value.split(" ");
					if (valueArr.length > 0) {
						for (String valueArg : valueArr) {
							listDataSystemdElement.add(new SystemdElement().fileName(valueArg));
						}
						return true;
					} else {
						listDataSystemdElement.add(new SystemdElement().fileName(value));
						return true;
					}

				} else if (type == String.class) {
					@SuppressWarnings("unchecked")
					CopyOnWriteArrayList<String> listDataSystemdElement = (CopyOnWriteArrayList<String>) listData;
					listDataSystemdElement.add(value);
					return true;
				}

				else if (type == CAPs.class) {
					@SuppressWarnings("unchecked")
					CopyOnWriteArrayList<CAPs> listDataSystemdElement = (CopyOnWriteArrayList<CAPs>) listData;

					String[] valueArr = value.split(" ");
					if (valueArr.length > 0) {
						for (String valueArg : valueArr) {
							listDataSystemdElement.add(CAPs.valueOf(valueArg));
						}
						return true;
					} else {
						listDataSystemdElement.add(CAPs.valueOf(value));
						return true;
					}
				} else if (type == RestrictAddressFamiliesType.class) {
					@SuppressWarnings("unchecked")
					CopyOnWriteArrayList<RestrictAddressFamiliesType> listDataSystemdElement = (CopyOnWriteArrayList<RestrictAddressFamiliesType>) listData;

					String[] valueArr = value.split(" ");
					if (valueArr.length > 0) {
						for (String valueArg : valueArr) {
							listDataSystemdElement.add(RestrictAddressFamiliesType.valueOf(valueArg));
						}
						return true;
					} else {
						listDataSystemdElement.add(RestrictAddressFamiliesType.valueOf(value));
						return true;
					}
				}

				else {
					throw new NotParsedException(String.format(
							"Добавить обработку коллекции с типом [%s] ДЛЯ: section:[%s] key:[%s] value:[%s] ",
							type.getSimpleName(), section.getClass().getSimpleName(), realKey, value));
				}

			}
			return false;
		} else if (keyType == Boolean.class) {
			if (keyField.trySetAccessible()) {
				keyField.set(section, parseYesNoBoolean(value));
				return true;
			}
			throw new NotParsedException(String.format("не установлено значение [%s] для секции [%s] ключа [%s]",
					keyType.getSimpleName(), section.getClass().getSimpleName(), realKey));
		} else if (keyType == SystemdElement.class) {
			if (keyField.trySetAccessible()) {
				if (keyField.get(section) != null) {
					throw new NotParsedException(String.format(
							"SystemdParserKeyListener.setValueViaReflection.keyType == SystemdElement.class NOT NULL for section:[%s] key:[%s]",
							section, realKey));
				}

				keyField.set(section, new SystemdElement().fileName(value));
				return true;
			}
			throw new NotParsedException(String.format("не установлено значение [%s] для секции [%s] ключа [%s]",
					keyType.getSimpleName(), section.getClass().getSimpleName(), realKey));
		}

		else {
			throw new NotParsedException(String.format("Обработать тип [%s] для секции [%s] ключа [%s]",
					keyType.getSimpleName(), section.getClass().getSimpleName(), realKey));
		}
	}

	SECTIONNAME currentSectionName;

	SystemdParserListener listener = new SystemdParserListener() {

		@Override
		public void errorStream(String msg) throws Exception {
			throw new Exception(msg);
		}

		@Override
		public void input(String input) throws NotParsedException {
			if (input.trim().isEmpty()) {
				return;
			}
			if (itComment(input)) {
				return;
			}
			Optional<SECTIONNAME> sectionOptional = isSection(input);
			if (sectionOptional.isPresent()) {
				currentSectionName = sectionOptional.get();
				return;
			} else {
				String trimOnput = input.trim();
				if (trimOnput.startsWith("[") && trimOnput.endsWith("]")) {
					throw new NotParsedException(String
							.format("Не сменена секция %s (возможно её нет в SystemdElement.SECTIONNAME)", input));
				}
			}
			try {
				listener.withSectionName(currentSectionName, input);
			} catch (Exception e) {
				String msg = String.format("currentFile:[%s] currentSectionName:[%s] input:[%s]", currentFile,
						currentSectionName, input);
				throw new NotParsedException(msg, e);
			}
		}

		@Override
		public void withSection(Section section, String key, String value) throws Exception {
			if (!checkWithSection(currentFile, section, key, value)) {
				return;
			}
			parseWithSection(section, key, value);
		}

		@Override
		public void withSectionName(SECTIONNAME sectionName, String input) throws Exception {
			String[] dataArr = null;
			String key = null;
			String value = null;
			try {
				dataArr = input.split("\\=");
				key = dataArr[0];
				value = dataArr[1];
			} catch (Exception e) {// ignore
			}
			if (!checkWithSectionName(currentFile, sectionName, key, value)) {
				return;
			}

			Section section = Section.getSectionFor(currentFile, element, sectionName);
			listener.withSection(section, key, value);
		}
	};

	SystemdElement element;

	private File currentFile;

	protected boolean checkWithSection(File file, Section section, String key, String value)
			throws NullPointerException, IllegalArgumentException {
		if (key == null) {
			LOGGER.error("CHECK=checkWithSection key==null For sectionName:[{}] with VALUE:[{}] for File:[{}]", section,
					value, file);
			return false;
		}
		if (key.isEmpty()) {
			LOGGER.error("CHECK=checkWithSection key.isEmpty() for File:[{}]", file);
			return false;
		}
		if (value == null) {
			LOGGER.error("CHECK=checkWithSection value==null For sectionName:[{}] with KEY:[{}] for File:[{}]",
					Section.retNameFor(section.getName()), key, file);
			return false;
		}
		if (value.isEmpty()) {
			LOGGER.error("CHECK=checkWithSection value.isEmpty() for File:[{}]", file);
			return false;
		}
		return true;

	}

	protected boolean checkWithSectionName(File file, SECTIONNAME sectionName, String key, String value) {
		if (key == null) {
			LOGGER.error("CHECK=checkWithSectionName key==null For sectionName:[{}] with VALUE:[{}] for File:[{}]",
					sectionName, value, file);
			return false;
		}
		if (key.isEmpty()) {
			LOGGER.error("CHECK=checkWithSectionName key.isEmpty() for File:[{}]", file);
			return false;
		}
		if (value == null) {
			LOGGER.error("CHECK=checkWithSectionName value==null For sectionName:[{}] with VALUE:[{}] for File:[{}]",
					sectionName, key, file);
			return false;
		}
		if (value.isEmpty()) {
			LOGGER.error("CHECK=checkWithSectionName value.isEmpty() for File:[{}]", file);
			return false;
		}
		return true;
	}

	public SystemdParser file(File serviceFile) throws Exception {
		this.currentFile = serviceFile;
		String name = currentFile.getName();
		fileName(name);
		element.fileName(name);
		return this;
	}

	/**
	 *
	 * @param name FileName
	 *
	 * @implNote Caused by: java.lang.NullPointerException: Cannot invoke
	 *           "com.github.javlock.system.systemd.data.SystemdElement.getUnitSection()"
	 *           because "element" is null
	 * @return
	 * @throws Exception
	 */
	public SystemdParser fileName(String name) throws Exception {
		ELEMENTTYPE type = ServiceUtils.getElementType(name);

		switch (type) {
		case SERVICE: {
			element = new Service();
			break;
		}
		case TARGET:
			element = new Target();
			break;
		case TIMER:
			element = new Timer();
			break;
		case SOCKET:
			element = new SOCKET();
			break;
		case PATH:
			element = new PATH();
			break;
		case MOUNT:
			element = new MOUNT();
			break;
		case AUTOMOUNT:
			element = new AUTOMOUNT();
			break;
		case CONF:
			element = new CONF();
			break;
		case SLICE:
			element = new SLICE();
			break;
		default:
			String errorMsg = String.format(
					"Обработай в public SystemdParser fileName(String name) value:%s in section:%s in file:%s  ", type,
					currentSectionName, currentFile);
			listener.errorStream(errorMsg);
		}
		return this;
	}

	protected Optional<SECTIONNAME> isSection(String input) {
		for (SECTIONNAME sectionName : SECTIONNAME.values()) {
			String sectionNameF = Section.retNameFor(sectionName);
			if (input.equalsIgnoreCase(sectionNameF)) {
				return Optional.ofNullable(sectionName);
			}
		}
		return Optional.empty();
	}

	protected boolean itComment(String input) {
		String trim = input.trim();
		if (trim.startsWith(";")) {
			return true;
		}
		return trim.startsWith("#");
	}

	public SystemdElement parse(File serviceFile) throws Exception {
		List<String> data = Files.readAllLines(serviceFile.toPath(), StandardCharsets.UTF_8);
		return parse(data);
	}

	public SystemdElement parse(List<String> data) throws Exception {
		for (String string : data) {
			listener.input(string);
		}
		return element;
	}

	protected void parseWithSection(Section section, String key, String value) throws Exception {
		String realKey = SystDKeyMeta.findKeyInClass(key);
		if (ServiceUtils.containsKeyInSystemdElement(SystemdElement.class, realKey)) {
			if (setValueViaReflection(SystemdElement.class, section, realKey, value)) {
				// LOGGER.info("Установлен KEY:[{}] VALUE:[{}]", realKey, value);
			} else {
				String sectionNameWith = Section.retNameFor(section.getName());
				String msg = String.format("Не установлен withSection %s key [%s] value [%s]", sectionNameWith, key,
						value);
				throw new NotParsedException(msg);
			}
		} else {
			throw new NoSuchFieldException(
					String.format("Field:[%s] для value:[%s] нет в (SystemdElement.java:1)", key, value));
		}
	}

}
