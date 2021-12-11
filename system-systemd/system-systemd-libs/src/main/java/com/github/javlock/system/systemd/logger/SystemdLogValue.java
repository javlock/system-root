package com.github.javlock.system.systemd.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.javlock.system.JOB_TYPE;
import com.github.javlock.system.auditd._AUDIT_TYPE_NAME;

public class SystemdLogValue {
	String __CURSOR;// ID

	Long __MONOTONIC_TIMESTAMP;

	Long __REALTIME_TIMESTAMP;
	String _AUDIT_FIELD_A0;
	String _AUDIT_FIELD_A1;
	String _AUDIT_FIELD_A2;
	String _AUDIT_FIELD_A3;
	String _AUDIT_FIELD_APPARMOR;
	String _AUDIT_FIELD_ARCH;
	Integer _AUDIT_FIELD_CAP_FE;
	Integer _AUDIT_FIELD_CAP_FI;
	Integer _AUDIT_FIELD_CAP_FP;
	Integer _AUDIT_FIELD_CAP_FROOTID;
	Integer _AUDIT_FIELD_CAP_FVER;
	String _AUDIT_FIELD_CWD;
	String _AUDIT_FIELD_DENIED_MASK;
	String _AUDIT_FIELD_DEV;
	Integer _AUDIT_FIELD_ENTRIES;
	Integer _AUDIT_FIELD_EXIT;
	Integer _AUDIT_FIELD_FAMILY;
	Integer _AUDIT_FIELD_INODE;
	Integer _AUDIT_FIELD_ITEM;
	Integer _AUDIT_FIELD_ITEMS;
	String _AUDIT_FIELD_KEY;
	String _AUDIT_FIELD_MODE;
	String _AUDIT_FIELD_NAME;
	Integer _AUDIT_FIELD_OGID;
	String _AUDIT_FIELD_OP;
	String _AUDIT_FIELD_OPERATION;
	Integer _AUDIT_FIELD_OUID;
	Integer _AUDIT_FIELD_PROG_ID;
	Integer _AUDIT_FIELD_RES;
	Integer _AUDIT_FIELD_SGID;
	Integer _AUDIT_FIELD_SUID;
	Integer _AUDIT_FIELD_SYSCALL;
	Integer _AUDIT_ID;
	Long _AUDIT_LOGINUID;
	Long _AUDIT_SESSION;
	Integer _AUDIT_TYPE;
	_AUDIT_TYPE_NAME _AUDIT_TYPE_NAME;
	String _BOOT_ID;

	String _CAP_EFFECTIVE;
	String _CMDLINE;
	String _COMM;
	Integer _EGID;
	Integer _EUID;
	String _EXE;
	Integer _FSGID;
	Integer _FSUID;
	Integer _GID;
	String _HOSTNAME;
	String _KERNEL_DEVICE;
	String _MACHINE_ID;
	Long _PID;
	Integer _PPID;
	Long _SOURCE_MONOTONIC_TIMESTAMP;
	Long _SOURCE_REALTIME_TIMESTAMP;
	String _STREAM_ID;
	String _SYSTEMD_CGROUP;
	String _SYSTEMD_INVOCATION_ID;
	Integer _SYSTEMD_OWNER_UID;
	String _SYSTEMD_SLICE;// slice
	String _SYSTEMD_UNIT;// service
	String _SYSTEMD_USER_SLICE;// slice
	String _SYSTEMD_USER_UNIT;// UNIT
	String _TRANSPORT;
	String _TTY;
	String _UDEV_DEVNODE;
	String _UDEV_SYSNAME;
	Integer _UID;
	String AUDIT_FIELD_ACCT;
	String AUDIT_FIELD_ADDR;
	String AUDIT_FIELD_COMM;
	String AUDIT_FIELD_EXE;
	String AUDIT_FIELD_GRANTORS;
	String AUDIT_FIELD_HOSTNAME;
	String AUDIT_FIELD_OP;
	Integer AUDIT_FIELD_PID;
	String AUDIT_FIELD_TERMINAL;
	Integer AUDIT_FIELD_UID;
	String AUDIT_FIELD_UNIT;
	String AUDIT_FIELD_UUID;
	Long AVAILABLE;
	String AVAILABLE_PRETTY;
	String CODE_FILE;
	String CODE_FUNC;
	Integer CODE_LINE;
	String COMMAND;
	Long CPU_USAGE_NSEC;
	Long CURRENT_USE;
	String CURRENT_USE_PRETTY;
	Long DISK_AVAILABLE;
	String DISK_AVAILABLE_PRETTY;
	Long DISK_KEEP_FREE;
	String DISK_KEEP_FREE_PRETTY;
	Integer EXIT_STATUS;
	String GLIB_DOMAIN;
	String INVOCATION_ID;
	Integer JOB_ID;
	String JOB_RESULT;
	JOB_TYPE JOB_TYPE;
	String JOURNAL_NAME;
	String JOURNAL_PATH;
	Integer LEADER;
	Long LIMIT;
	String LIMIT_PRETTY;
	Long MAX_USE;
	String MAX_USE_PRETTY;
	String MESSAGE;
	String MESSAGE_ID;
	String NM_CONNECTION;
	String NM_DEVICE;
	String NM_LOG_DOMAINS;
	Integer PRIORITY;
	String PULSE_BACKTRACE;
	String _AUDIT_FIELD_RDEV;
	String QT_CATEGORY;
	ArrayList<String> _UDEV_DEVLINK;
	String _AUDIT_FIELD_NAMETYPE;
	String NM_LOG_LEVEL;

	Integer SESSION_ID;
	Integer _SYSTEMD_SESSION;
	Integer _AUDIT_FIELD_AUDIT_PID;
	Integer _AUDIT_FIELD_AUDIT_ENABLED;
	Integer _AUDIT_FIELD_NL_MCGRP;
	Integer _AUDIT_FIELD_OLD;
	Integer SYSLOG_FACILITY;
	String SYSLOG_IDENTIFIER;
	Integer SYSLOG_PID;
	String SYSLOG_RAW;
	String SYSLOG_TIMESTAMP;
	Integer THREAD_ID;
	Integer TID;
	String TIMESTAMP_BOOTTIME;
	String TIMESTAMP_MONOTONIC;
	String UNIT;// UNIT
	String USER_ID;
	String _KERNEL_SUBSYSTEM;
	String _SELINUX_CONTEXT;
	String DEVICE;
	String _AUDIT_FIELD_PROFILE;
	String USER_INVOCATION_ID;
	String AUDIT_FIELD_NAME;
	String AUDIT_FIELD_RES;
	String _AUDIT_FIELD_TABLE;
	String _AUDIT_FIELD_TARGET;
	String _AUDIT_FIELD_SUCCESS;
	String SEAT_ID;
	String UNIT_RESULT;
	String _AUDIT_FIELD_REQUESTED_MASK;
	String AUDIT_FIELD_RESULT;
	String EXIT_CODE;
	String USER_UNIT;// UNIT
	Long KERNEL_USEC;
	Long USERSPACE_USEC;

	Integer _AUDIT_FIELD_SIG;
	String COREDUMP_CGROUP;
	String COREDUMP_CMDLINE;
	String COREDUMP_COMM;
	String COREDUMP_CWD;
	HashMap<String, String> COREDUMP_ENVIRON;
	String COREDUMP_EXE;
	String COREDUMP_FILENAME;
	Integer COREDUMP_GID;
	String COREDUMP_HOSTNAME;
	String COREDUMP_OPEN_FDS;
	Integer COREDUMP_OWNER_UID;
	Integer COREDUMP_PID;
	String COREDUMP_PROC_CGROUP;
	String COREDUMP_PROC_LIMITS;// ARRAY LINES
	CopyOnWriteArrayList<String> COREDUMP_PROC_MAPS;
	CopyOnWriteArrayList<String> COREDUMP_PROC_MOUNTINFO;
	CopyOnWriteArrayList<String> COREDUMP_PROC_STATUS;
	Double COREDUMP_RLIMIT;
	String COREDUMP_ROOT;
	Integer COREDUMP_SESSION;
	Integer COREDUMP_SIGNAL;
	String COREDUMP_SIGNAL_NAME;
	String COREDUMP_SLICE;// Slice
	Long COREDUMP_TIMESTAMP;
	Integer COREDUMP_UID;
	String COREDUMP_UNIT;// unit

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SystemdLogValue)) {
			return false;
		}
		SystemdLogValue other = (SystemdLogValue) obj;
		return Objects.equals(__CURSOR, other.__CURSOR);
	}

	@Override
	public int hashCode() {
		return Objects.hash(__CURSOR);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemdLogValue [");
		if (__CURSOR != null) {
			builder.append("__CURSOR=");
			builder.append(__CURSOR);
			builder.append(", ");
		}
		if (__MONOTONIC_TIMESTAMP != null) {
			builder.append("__MONOTONIC_TIMESTAMP=");
			builder.append(__MONOTONIC_TIMESTAMP);
			builder.append(", ");
		}
		if (__REALTIME_TIMESTAMP != null) {
			builder.append("__REALTIME_TIMESTAMP=");
			builder.append(__REALTIME_TIMESTAMP);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_A0 != null) {
			builder.append("_AUDIT_FIELD_A0=");
			builder.append(_AUDIT_FIELD_A0);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_A1 != null) {
			builder.append("_AUDIT_FIELD_A1=");
			builder.append(_AUDIT_FIELD_A1);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_A2 != null) {
			builder.append("_AUDIT_FIELD_A2=");
			builder.append(_AUDIT_FIELD_A2);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_A3 != null) {
			builder.append("_AUDIT_FIELD_A3=");
			builder.append(_AUDIT_FIELD_A3);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_APPARMOR != null) {
			builder.append("_AUDIT_FIELD_APPARMOR=");
			builder.append(_AUDIT_FIELD_APPARMOR);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_ARCH != null) {
			builder.append("_AUDIT_FIELD_ARCH=");
			builder.append(_AUDIT_FIELD_ARCH);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CAP_FE != null) {
			builder.append("_AUDIT_FIELD_CAP_FE=");
			builder.append(_AUDIT_FIELD_CAP_FE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CAP_FI != null) {
			builder.append("_AUDIT_FIELD_CAP_FI=");
			builder.append(_AUDIT_FIELD_CAP_FI);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CAP_FP != null) {
			builder.append("_AUDIT_FIELD_CAP_FP=");
			builder.append(_AUDIT_FIELD_CAP_FP);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CAP_FROOTID != null) {
			builder.append("_AUDIT_FIELD_CAP_FROOTID=");
			builder.append(_AUDIT_FIELD_CAP_FROOTID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CAP_FVER != null) {
			builder.append("_AUDIT_FIELD_CAP_FVER=");
			builder.append(_AUDIT_FIELD_CAP_FVER);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_CWD != null) {
			builder.append("_AUDIT_FIELD_CWD=");
			builder.append(_AUDIT_FIELD_CWD);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_DENIED_MASK != null) {
			builder.append("_AUDIT_FIELD_DENIED_MASK=");
			builder.append(_AUDIT_FIELD_DENIED_MASK);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_DEV != null) {
			builder.append("_AUDIT_FIELD_DEV=");
			builder.append(_AUDIT_FIELD_DEV);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_ENTRIES != null) {
			builder.append("_AUDIT_FIELD_ENTRIES=");
			builder.append(_AUDIT_FIELD_ENTRIES);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_EXIT != null) {
			builder.append("_AUDIT_FIELD_EXIT=");
			builder.append(_AUDIT_FIELD_EXIT);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_FAMILY != null) {
			builder.append("_AUDIT_FIELD_FAMILY=");
			builder.append(_AUDIT_FIELD_FAMILY);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_INODE != null) {
			builder.append("_AUDIT_FIELD_INODE=");
			builder.append(_AUDIT_FIELD_INODE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_ITEM != null) {
			builder.append("_AUDIT_FIELD_ITEM=");
			builder.append(_AUDIT_FIELD_ITEM);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_ITEMS != null) {
			builder.append("_AUDIT_FIELD_ITEMS=");
			builder.append(_AUDIT_FIELD_ITEMS);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_KEY != null) {
			builder.append("_AUDIT_FIELD_KEY=");
			builder.append(_AUDIT_FIELD_KEY);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_MODE != null) {
			builder.append("_AUDIT_FIELD_MODE=");
			builder.append(_AUDIT_FIELD_MODE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_NAME != null) {
			builder.append("_AUDIT_FIELD_NAME=");
			builder.append(_AUDIT_FIELD_NAME);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_OGID != null) {
			builder.append("_AUDIT_FIELD_OGID=");
			builder.append(_AUDIT_FIELD_OGID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_OP != null) {
			builder.append("_AUDIT_FIELD_OP=");
			builder.append(_AUDIT_FIELD_OP);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_OPERATION != null) {
			builder.append("_AUDIT_FIELD_OPERATION=");
			builder.append(_AUDIT_FIELD_OPERATION);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_OUID != null) {
			builder.append("_AUDIT_FIELD_OUID=");
			builder.append(_AUDIT_FIELD_OUID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_PROG_ID != null) {
			builder.append("_AUDIT_FIELD_PROG_ID=");
			builder.append(_AUDIT_FIELD_PROG_ID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_RES != null) {
			builder.append("_AUDIT_FIELD_RES=");
			builder.append(_AUDIT_FIELD_RES);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_SGID != null) {
			builder.append("_AUDIT_FIELD_SGID=");
			builder.append(_AUDIT_FIELD_SGID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_SUID != null) {
			builder.append("_AUDIT_FIELD_SUID=");
			builder.append(_AUDIT_FIELD_SUID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_SYSCALL != null) {
			builder.append("_AUDIT_FIELD_SYSCALL=");
			builder.append(_AUDIT_FIELD_SYSCALL);
			builder.append(", ");
		}
		if (_AUDIT_ID != null) {
			builder.append("_AUDIT_ID=");
			builder.append(_AUDIT_ID);
			builder.append(", ");
		}
		if (_AUDIT_LOGINUID != null) {
			builder.append("_AUDIT_LOGINUID=");
			builder.append(_AUDIT_LOGINUID);
			builder.append(", ");
		}
		if (_AUDIT_SESSION != null) {
			builder.append("_AUDIT_SESSION=");
			builder.append(_AUDIT_SESSION);
			builder.append(", ");
		}
		if (_AUDIT_TYPE != null) {
			builder.append("_AUDIT_TYPE=");
			builder.append(_AUDIT_TYPE);
			builder.append(", ");
		}
		if (_AUDIT_TYPE_NAME != null) {
			builder.append("_AUDIT_TYPE_NAME=");
			builder.append(_AUDIT_TYPE_NAME);
			builder.append(", ");
		}
		if (_BOOT_ID != null) {
			builder.append("_BOOT_ID=");
			builder.append(_BOOT_ID);
			builder.append(", ");
		}
		if (_CAP_EFFECTIVE != null) {
			builder.append("_CAP_EFFECTIVE=");
			builder.append(_CAP_EFFECTIVE);
			builder.append(", ");
		}
		if (_CMDLINE != null) {
			builder.append("_CMDLINE=");
			builder.append(_CMDLINE);
			builder.append(", ");
		}
		if (_COMM != null) {
			builder.append("_COMM=");
			builder.append(_COMM);
			builder.append(", ");
		}
		if (_EGID != null) {
			builder.append("_EGID=");
			builder.append(_EGID);
			builder.append(", ");
		}
		if (_EUID != null) {
			builder.append("_EUID=");
			builder.append(_EUID);
			builder.append(", ");
		}
		if (_EXE != null) {
			builder.append("_EXE=");
			builder.append(_EXE);
			builder.append(", ");
		}
		if (_FSGID != null) {
			builder.append("_FSGID=");
			builder.append(_FSGID);
			builder.append(", ");
		}
		if (_FSUID != null) {
			builder.append("_FSUID=");
			builder.append(_FSUID);
			builder.append(", ");
		}
		if (_GID != null) {
			builder.append("_GID=");
			builder.append(_GID);
			builder.append(", ");
		}
		if (_HOSTNAME != null) {
			builder.append("_HOSTNAME=");
			builder.append(_HOSTNAME);
			builder.append(", ");
		}
		if (_KERNEL_DEVICE != null) {
			builder.append("_KERNEL_DEVICE=");
			builder.append(_KERNEL_DEVICE);
			builder.append(", ");
		}
		if (_MACHINE_ID != null) {
			builder.append("_MACHINE_ID=");
			builder.append(_MACHINE_ID);
			builder.append(", ");
		}
		if (_PID != null) {
			builder.append("_PID=");
			builder.append(_PID);
			builder.append(", ");
		}
		if (_PPID != null) {
			builder.append("_PPID=");
			builder.append(_PPID);
			builder.append(", ");
		}
		if (_SOURCE_MONOTONIC_TIMESTAMP != null) {
			builder.append("_SOURCE_MONOTONIC_TIMESTAMP=");
			builder.append(_SOURCE_MONOTONIC_TIMESTAMP);
			builder.append(", ");
		}
		if (_SOURCE_REALTIME_TIMESTAMP != null) {
			builder.append("_SOURCE_REALTIME_TIMESTAMP=");
			builder.append(_SOURCE_REALTIME_TIMESTAMP);
			builder.append(", ");
		}
		if (_STREAM_ID != null) {
			builder.append("_STREAM_ID=");
			builder.append(_STREAM_ID);
			builder.append(", ");
		}
		if (_SYSTEMD_CGROUP != null) {
			builder.append("_SYSTEMD_CGROUP=");
			builder.append(_SYSTEMD_CGROUP);
			builder.append(", ");
		}
		if (_SYSTEMD_INVOCATION_ID != null) {
			builder.append("_SYSTEMD_INVOCATION_ID=");
			builder.append(_SYSTEMD_INVOCATION_ID);
			builder.append(", ");
		}
		if (_SYSTEMD_OWNER_UID != null) {
			builder.append("_SYSTEMD_OWNER_UID=");
			builder.append(_SYSTEMD_OWNER_UID);
			builder.append(", ");
		}
		if (_SYSTEMD_SLICE != null) {
			builder.append("_SYSTEMD_SLICE=");
			builder.append(_SYSTEMD_SLICE);
			builder.append(", ");
		}
		if (_SYSTEMD_UNIT != null) {
			builder.append("_SYSTEMD_UNIT=");
			builder.append(_SYSTEMD_UNIT);
			builder.append(", ");
		}
		if (_SYSTEMD_USER_SLICE != null) {
			builder.append("_SYSTEMD_USER_SLICE=");
			builder.append(_SYSTEMD_USER_SLICE);
			builder.append(", ");
		}
		if (_SYSTEMD_USER_UNIT != null) {
			builder.append("_SYSTEMD_USER_UNIT=");
			builder.append(_SYSTEMD_USER_UNIT);
			builder.append(", ");
		}
		if (_TRANSPORT != null) {
			builder.append("_TRANSPORT=");
			builder.append(_TRANSPORT);
			builder.append(", ");
		}
		if (_TTY != null) {
			builder.append("_TTY=");
			builder.append(_TTY);
			builder.append(", ");
		}
		if (_UDEV_DEVNODE != null) {
			builder.append("_UDEV_DEVNODE=");
			builder.append(_UDEV_DEVNODE);
			builder.append(", ");
		}
		if (_UDEV_SYSNAME != null) {
			builder.append("_UDEV_SYSNAME=");
			builder.append(_UDEV_SYSNAME);
			builder.append(", ");
		}
		if (_UID != null) {
			builder.append("_UID=");
			builder.append(_UID);
			builder.append(", ");
		}
		if (AUDIT_FIELD_ACCT != null) {
			builder.append("AUDIT_FIELD_ACCT=");
			builder.append(AUDIT_FIELD_ACCT);
			builder.append(", ");
		}
		if (AUDIT_FIELD_ADDR != null) {
			builder.append("AUDIT_FIELD_ADDR=");
			builder.append(AUDIT_FIELD_ADDR);
			builder.append(", ");
		}
		if (AUDIT_FIELD_COMM != null) {
			builder.append("AUDIT_FIELD_COMM=");
			builder.append(AUDIT_FIELD_COMM);
			builder.append(", ");
		}
		if (AUDIT_FIELD_EXE != null) {
			builder.append("AUDIT_FIELD_EXE=");
			builder.append(AUDIT_FIELD_EXE);
			builder.append(", ");
		}
		if (AUDIT_FIELD_GRANTORS != null) {
			builder.append("AUDIT_FIELD_GRANTORS=");
			builder.append(AUDIT_FIELD_GRANTORS);
			builder.append(", ");
		}
		if (AUDIT_FIELD_HOSTNAME != null) {
			builder.append("AUDIT_FIELD_HOSTNAME=");
			builder.append(AUDIT_FIELD_HOSTNAME);
			builder.append(", ");
		}
		if (AUDIT_FIELD_OP != null) {
			builder.append("AUDIT_FIELD_OP=");
			builder.append(AUDIT_FIELD_OP);
			builder.append(", ");
		}
		if (AUDIT_FIELD_PID != null) {
			builder.append("AUDIT_FIELD_PID=");
			builder.append(AUDIT_FIELD_PID);
			builder.append(", ");
		}
		if (AUDIT_FIELD_TERMINAL != null) {
			builder.append("AUDIT_FIELD_TERMINAL=");
			builder.append(AUDIT_FIELD_TERMINAL);
			builder.append(", ");
		}
		if (AUDIT_FIELD_UID != null) {
			builder.append("AUDIT_FIELD_UID=");
			builder.append(AUDIT_FIELD_UID);
			builder.append(", ");
		}
		if (AUDIT_FIELD_UNIT != null) {
			builder.append("AUDIT_FIELD_UNIT=");
			builder.append(AUDIT_FIELD_UNIT);
			builder.append(", ");
		}
		if (AUDIT_FIELD_UUID != null) {
			builder.append("AUDIT_FIELD_UUID=");
			builder.append(AUDIT_FIELD_UUID);
			builder.append(", ");
		}
		if (AVAILABLE != null) {
			builder.append("AVAILABLE=");
			builder.append(AVAILABLE);
			builder.append(", ");
		}
		if (AVAILABLE_PRETTY != null) {
			builder.append("AVAILABLE_PRETTY=");
			builder.append(AVAILABLE_PRETTY);
			builder.append(", ");
		}
		if (CODE_FILE != null) {
			builder.append("CODE_FILE=");
			builder.append(CODE_FILE);
			builder.append(", ");
		}
		if (CODE_FUNC != null) {
			builder.append("CODE_FUNC=");
			builder.append(CODE_FUNC);
			builder.append(", ");
		}
		if (CODE_LINE != null) {
			builder.append("CODE_LINE=");
			builder.append(CODE_LINE);
			builder.append(", ");
		}
		if (COMMAND != null) {
			builder.append("COMMAND=");
			builder.append(COMMAND);
			builder.append(", ");
		}
		if (CPU_USAGE_NSEC != null) {
			builder.append("CPU_USAGE_NSEC=");
			builder.append(CPU_USAGE_NSEC);
			builder.append(", ");
		}
		if (CURRENT_USE != null) {
			builder.append("CURRENT_USE=");
			builder.append(CURRENT_USE);
			builder.append(", ");
		}
		if (CURRENT_USE_PRETTY != null) {
			builder.append("CURRENT_USE_PRETTY=");
			builder.append(CURRENT_USE_PRETTY);
			builder.append(", ");
		}
		if (DISK_AVAILABLE != null) {
			builder.append("DISK_AVAILABLE=");
			builder.append(DISK_AVAILABLE);
			builder.append(", ");
		}
		if (DISK_AVAILABLE_PRETTY != null) {
			builder.append("DISK_AVAILABLE_PRETTY=");
			builder.append(DISK_AVAILABLE_PRETTY);
			builder.append(", ");
		}
		if (DISK_KEEP_FREE != null) {
			builder.append("DISK_KEEP_FREE=");
			builder.append(DISK_KEEP_FREE);
			builder.append(", ");
		}
		if (DISK_KEEP_FREE_PRETTY != null) {
			builder.append("DISK_KEEP_FREE_PRETTY=");
			builder.append(DISK_KEEP_FREE_PRETTY);
			builder.append(", ");
		}
		if (EXIT_STATUS != null) {
			builder.append("EXIT_STATUS=");
			builder.append(EXIT_STATUS);
			builder.append(", ");
		}
		if (GLIB_DOMAIN != null) {
			builder.append("GLIB_DOMAIN=");
			builder.append(GLIB_DOMAIN);
			builder.append(", ");
		}
		if (INVOCATION_ID != null) {
			builder.append("INVOCATION_ID=");
			builder.append(INVOCATION_ID);
			builder.append(", ");
		}
		if (JOB_ID != null) {
			builder.append("JOB_ID=");
			builder.append(JOB_ID);
			builder.append(", ");
		}
		if (JOB_RESULT != null) {
			builder.append("JOB_RESULT=");
			builder.append(JOB_RESULT);
			builder.append(", ");
		}
		if (JOB_TYPE != null) {
			builder.append("JOB_TYPE=");
			builder.append(JOB_TYPE);
			builder.append(", ");
		}
		if (JOURNAL_NAME != null) {
			builder.append("JOURNAL_NAME=");
			builder.append(JOURNAL_NAME);
			builder.append(", ");
		}
		if (JOURNAL_PATH != null) {
			builder.append("JOURNAL_PATH=");
			builder.append(JOURNAL_PATH);
			builder.append(", ");
		}
		if (LEADER != null) {
			builder.append("LEADER=");
			builder.append(LEADER);
			builder.append(", ");
		}
		if (LIMIT != null) {
			builder.append("LIMIT=");
			builder.append(LIMIT);
			builder.append(", ");
		}
		if (LIMIT_PRETTY != null) {
			builder.append("LIMIT_PRETTY=");
			builder.append(LIMIT_PRETTY);
			builder.append(", ");
		}
		if (MAX_USE != null) {
			builder.append("MAX_USE=");
			builder.append(MAX_USE);
			builder.append(", ");
		}
		if (MAX_USE_PRETTY != null) {
			builder.append("MAX_USE_PRETTY=");
			builder.append(MAX_USE_PRETTY);
			builder.append(", ");
		}
		if (MESSAGE != null) {
			builder.append("MESSAGE=");
			builder.append(MESSAGE);
			builder.append(", ");
		}
		if (MESSAGE_ID != null) {
			builder.append("MESSAGE_ID=");
			builder.append(MESSAGE_ID);
			builder.append(", ");
		}
		if (NM_CONNECTION != null) {
			builder.append("NM_CONNECTION=");
			builder.append(NM_CONNECTION);
			builder.append(", ");
		}
		if (NM_DEVICE != null) {
			builder.append("NM_DEVICE=");
			builder.append(NM_DEVICE);
			builder.append(", ");
		}
		if (NM_LOG_DOMAINS != null) {
			builder.append("NM_LOG_DOMAINS=");
			builder.append(NM_LOG_DOMAINS);
			builder.append(", ");
		}
		if (PRIORITY != null) {
			builder.append("PRIORITY=");
			builder.append(PRIORITY);
			builder.append(", ");
		}
		if (PULSE_BACKTRACE != null) {
			builder.append("PULSE_BACKTRACE=");
			builder.append(PULSE_BACKTRACE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_RDEV != null) {
			builder.append("_AUDIT_FIELD_RDEV=");
			builder.append(_AUDIT_FIELD_RDEV);
			builder.append(", ");
		}
		if (QT_CATEGORY != null) {
			builder.append("QT_CATEGORY=");
			builder.append(QT_CATEGORY);
			builder.append(", ");
		}
		if (_UDEV_DEVLINK != null) {
			builder.append("_UDEV_DEVLINK=");
			builder.append(_UDEV_DEVLINK);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_NAMETYPE != null) {
			builder.append("_AUDIT_FIELD_NAMETYPE=");
			builder.append(_AUDIT_FIELD_NAMETYPE);
			builder.append(", ");
		}
		if (NM_LOG_LEVEL != null) {
			builder.append("NM_LOG_LEVEL=");
			builder.append(NM_LOG_LEVEL);
			builder.append(", ");
		}
		if (SESSION_ID != null) {
			builder.append("SESSION_ID=");
			builder.append(SESSION_ID);
			builder.append(", ");
		}
		if (_SYSTEMD_SESSION != null) {
			builder.append("_SYSTEMD_SESSION=");
			builder.append(_SYSTEMD_SESSION);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_AUDIT_PID != null) {
			builder.append("_AUDIT_FIELD_AUDIT_PID=");
			builder.append(_AUDIT_FIELD_AUDIT_PID);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_AUDIT_ENABLED != null) {
			builder.append("_AUDIT_FIELD_AUDIT_ENABLED=");
			builder.append(_AUDIT_FIELD_AUDIT_ENABLED);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_NL_MCGRP != null) {
			builder.append("_AUDIT_FIELD_NL_MCGRP=");
			builder.append(_AUDIT_FIELD_NL_MCGRP);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_OLD != null) {
			builder.append("_AUDIT_FIELD_OLD=");
			builder.append(_AUDIT_FIELD_OLD);
			builder.append(", ");
		}
		if (SYSLOG_FACILITY != null) {
			builder.append("SYSLOG_FACILITY=");
			builder.append(SYSLOG_FACILITY);
			builder.append(", ");
		}
		if (SYSLOG_IDENTIFIER != null) {
			builder.append("SYSLOG_IDENTIFIER=");
			builder.append(SYSLOG_IDENTIFIER);
			builder.append(", ");
		}
		if (SYSLOG_PID != null) {
			builder.append("SYSLOG_PID=");
			builder.append(SYSLOG_PID);
			builder.append(", ");
		}
		if (SYSLOG_RAW != null) {
			builder.append("SYSLOG_RAW=");
			builder.append(SYSLOG_RAW);
			builder.append(", ");
		}
		if (SYSLOG_TIMESTAMP != null) {
			builder.append("SYSLOG_TIMESTAMP=");
			builder.append(SYSLOG_TIMESTAMP);
			builder.append(", ");
		}
		if (THREAD_ID != null) {
			builder.append("THREAD_ID=");
			builder.append(THREAD_ID);
			builder.append(", ");
		}
		if (TID != null) {
			builder.append("TID=");
			builder.append(TID);
			builder.append(", ");
		}
		if (TIMESTAMP_BOOTTIME != null) {
			builder.append("TIMESTAMP_BOOTTIME=");
			builder.append(TIMESTAMP_BOOTTIME);
			builder.append(", ");
		}
		if (TIMESTAMP_MONOTONIC != null) {
			builder.append("TIMESTAMP_MONOTONIC=");
			builder.append(TIMESTAMP_MONOTONIC);
			builder.append(", ");
		}
		if (UNIT != null) {
			builder.append("UNIT=");
			builder.append(UNIT);
			builder.append(", ");
		}
		if (USER_ID != null) {
			builder.append("USER_ID=");
			builder.append(USER_ID);
			builder.append(", ");
		}
		if (_KERNEL_SUBSYSTEM != null) {
			builder.append("_KERNEL_SUBSYSTEM=");
			builder.append(_KERNEL_SUBSYSTEM);
			builder.append(", ");
		}
		if (_SELINUX_CONTEXT != null) {
			builder.append("_SELINUX_CONTEXT=");
			builder.append(_SELINUX_CONTEXT);
			builder.append(", ");
		}
		if (DEVICE != null) {
			builder.append("DEVICE=");
			builder.append(DEVICE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_PROFILE != null) {
			builder.append("_AUDIT_FIELD_PROFILE=");
			builder.append(_AUDIT_FIELD_PROFILE);
			builder.append(", ");
		}
		if (USER_INVOCATION_ID != null) {
			builder.append("USER_INVOCATION_ID=");
			builder.append(USER_INVOCATION_ID);
			builder.append(", ");
		}
		if (AUDIT_FIELD_NAME != null) {
			builder.append("AUDIT_FIELD_NAME=");
			builder.append(AUDIT_FIELD_NAME);
			builder.append(", ");
		}
		if (AUDIT_FIELD_RES != null) {
			builder.append("AUDIT_FIELD_RES=");
			builder.append(AUDIT_FIELD_RES);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_TABLE != null) {
			builder.append("_AUDIT_FIELD_TABLE=");
			builder.append(_AUDIT_FIELD_TABLE);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_TARGET != null) {
			builder.append("_AUDIT_FIELD_TARGET=");
			builder.append(_AUDIT_FIELD_TARGET);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_SUCCESS != null) {
			builder.append("_AUDIT_FIELD_SUCCESS=");
			builder.append(_AUDIT_FIELD_SUCCESS);
			builder.append(", ");
		}
		if (SEAT_ID != null) {
			builder.append("SEAT_ID=");
			builder.append(SEAT_ID);
			builder.append(", ");
		}
		if (UNIT_RESULT != null) {
			builder.append("UNIT_RESULT=");
			builder.append(UNIT_RESULT);
			builder.append(", ");
		}
		if (_AUDIT_FIELD_REQUESTED_MASK != null) {
			builder.append("_AUDIT_FIELD_REQUESTED_MASK=");
			builder.append(_AUDIT_FIELD_REQUESTED_MASK);
			builder.append(", ");
		}
		if (AUDIT_FIELD_RESULT != null) {
			builder.append("AUDIT_FIELD_RESULT=");
			builder.append(AUDIT_FIELD_RESULT);
			builder.append(", ");
		}
		if (EXIT_CODE != null) {
			builder.append("EXIT_CODE=");
			builder.append(EXIT_CODE);
			builder.append(", ");
		}
		if (USER_UNIT != null) {
			builder.append("USER_UNIT=");
			builder.append(USER_UNIT);
			builder.append(", ");
		}
		if (KERNEL_USEC != null) {
			builder.append("KERNEL_USEC=");
			builder.append(KERNEL_USEC);
			builder.append(", ");
		}
		if (USERSPACE_USEC != null) {
			builder.append("USERSPACE_USEC=");
			builder.append(USERSPACE_USEC);
		}
		builder.append("]");
		return builder.toString();
	}
}
