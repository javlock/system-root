package com.github.javlock.system.apidata.systemd.data;

import java.lang.reflect.Field;

public class SystDKeyMeta {

	public static final String ACCEPT = "Accept";
	public static final String ACCURACYSES = "AccuracySec";
	public static final String AFTER = "After";
	public static final String ALIAS = "Alias";
	public static final String ALLOWISOLATE = "AllowIsolate";
	public static final String ALSO = "Also";
	public static final String AMBIENTCAPABILITIES = "AmbientCapabilities";
	public static final String ASSERTPATHEXISTS = "AssertPathExists";
	public static final String BEFORE = "Before";
	public static final String BINDIPV6ONLY = "BindIPv6Only";
	public static final String BINDSTO = "BindsTo";
	public static final String BUSNAME = "BusName";
	public static final String CACHEDIRECTORY = "CacheDirectory";
	public static final String CAPABILITYBOUNDINGSET = "CapabilityBoundingSet";
	public static final String CONDITIONACPOWER = "ConditionACPower";
	public static final String CONDITIONCAPABILITY = "ConditionCapability";
	public static final String CONDITIONCONTROLGROUPCONTROLLER = "ConditionControlGroupController";
	public static final String CONDITIONDIRECTORYNOTEMPTY = "ConditionDirectoryNotEmpty";
	public static final String CONDITIONFILEISEXECUTABLE = "ConditionFileIsExecutable";
	public static final String CONDITIONFILENOTEMPTY = "ConditionFileNotEmpty";
	public static final String CONDITIONFIRSTBOOT = "ConditionFirstBoot";
	public static final String CONDITIONKERNELCOMMANDLINE = "ConditionKernelCommandLine";
	public static final String CONDITIONNEEDSUPDATE = "ConditionNeedsUpdate";
	public static final String CONDITIONPATHEXISTS = "ConditionPathExists";
	public static final String CONDITIONPATHEXISTSGLOB = "ConditionPathExistsGlob";
	public static final String CONDITIONPATHISDIRECTORY = "ConditionPathIsDirectory";
	public static final String CONDITIONPATHISMOUNTPOINT = "ConditionPathIsMountPoint";
	public static final String CONDITIONPATHISREADWRITE = "ConditionPathIsReadWrite";
	public static final String CONDITIONPATHISSYMBOLICLINK = "ConditionPathIsSymbolicLink";
	public static final String CONDITIONSECURITY = "ConditionSecurity";
	public static final String CONDITIONVIRTUALIZATION = "ConditionVirtualization";
	public static final String CONFIGURATIONDIRECTORY = "ConfigurationDirectory";
	public static final String CONFIGURATIONDIRECTORYMODE = "ConfigurationDirectoryMode";
	public static final String CONFLICTS = "Conflicts";
	public static final String CPUACCOUNTING = "CPUAccounting";
	public static final String CPUSCHEDULINGPOLICY = "CPUSchedulingPolicy";
	public static final String CPUSCHEDULINGPRIORITY = "CPUSchedulingPriority";
	public static final String DEFAULTDEPENDENCIES = "DefaultDependencies";
	public static final String DEFAULTINSTANCE = "DefaultInstance";
	public static final String DELEGATE = "Delegate";
	public static final String DESCRIPTION = "Description";
	public static final String DEVICEALLOW = "DeviceAllow";
	public static final String DEVICEPOLICY = "DevicePolicy";
	public static final String DIRECTORYNOTEMPTY = "DirectoryNotEmpty";
	public static final String DOCUMENTATION = "Documentation";
	public static final String DYNAMICUSER = "DynamicUser";
	public static final String ENVIRONMENT = "Environment";
	public static final String ENVIRONMENTFILE = "EnvironmentFile";
	public static final String EXECRELOAD = "ExecReload";
	public static final String EXECSTART = "ExecStart";
	public static final String EXECSTARTPOST = "ExecStartPost";
	public static final String EXECSTARTPRE = "ExecStartPre";
	public static final String EXECSTOP = "ExecStop";
	public static final String EXECSTOPPOST = "ExecStopPost";
	public static final String FAILUREACTION = "FailureAction";
	public static final String FILEDESCRIPTORNAME = "FileDescriptorName";
	public static final String FILEDESCRIPTORSTOREMAX = "FileDescriptorStoreMax";
	public static final String GROUP = "Group";
	public static final String IGNOREONISOLATE = "IgnoreOnIsolate";
	public static final String IGNORESIGPIPE = "IgnoreSIGPIPE";
	public static final String IOSCHEDULINGCLASS = "IOSchedulingClass";
	public static final String IOSCHEDULINGPRIORITY = "IOSchedulingPriority";
	public static final String IPADDRESSDENY = "IPAddressDeny";
	public static final String JOBTIMEOUTACTION = "JobTimeoutAction";
	public static final String JOBTIMEOUTSEC = "JobTimeoutSec";
	public static final String KEYRINGMODE = "KeyringMode";
	public static final String KILLMODE = "KillMode";
	public static final String KILLSIGNAL = "KillSignal";
	public static final String LIMITCORE = "LimitCORE";
	public static final String LIMITMEMLOCK = "LimitMEMLOCK";
	public static final String LIMITNOFILE = "LimitNOFILE";
	public static final String LIMITNPROC = "LimitNPROC";
	public static final String LIMITRTPRIO = "LimitRTPRIO";
	public static final String LISTENDATAGRAM = "ListenDatagram";
	public static final String LISTENFIFO = "ListenFIFO";
	public static final String LISTENNETLINK = "ListenNetlink";
	public static final String LISTENSEQUENTIALPACKET = "ListenSequentialPacket";
	public static final String LISTENSPECIAL = "ListenSpecial";
	public static final String LISTENSTREAM = "ListenStream";
	public static final String LOADCREDENTIAL = "LoadCredential";
	public static final String LOCKPERSONALITY = "LockPersonality";
	public static final String LOGSDIRECTORY = "LogsDirectory";
	public static final String LOGSDIRECTORYMODE = "LogsDirectoryMode";
	public static final String MAKEDIRECTORY = "MakeDirectory";
	public static final String MAXCONNECTIONS = "MaxConnections";
	public static final String MEMORYACCOUNTING = "MemoryAccounting";
	public static final String MEMORYDENYWRITEEXECUTE = "MemoryDenyWriteExecute";
	public static final String MEMORYLOW = "MemoryLow";
	public static final String MEMORYMIN = "MemoryMin";
	public static final String NICE = "Nice";
	public static final String NONBLOCKIN = "NonBlocking";
	public static final String NONEWPRIVILEGES = "NoNewPrivileges";
	public static final String NOTIFYACCESS = "NotifyAccess";
	public static final String ONACTIVESEC = "OnActiveSec";
	public static final String ONBOOTSEC = "OnBootSec";
	public static final String ONCALENDAR = "OnCalendar";
	public static final String ONFAILUREJOBMODE = "OnFailureJobMode";
	public static final String ONFAILURE = "OnFailure";
	public static final String ONUNITACTIVESEC = "OnUnitActiveSec";
	public static final String OOMSCOREADJUST = "OOMScoreAdjust";
	public static final String OPTIONS = "Options";
	public static final String PAMNAME = "PAMName";
	public static final String PARTOF = "PartOf";
	public static final String PASSCREDENTIALS = "PassCredentials";
	public static final String PASSPACKETINFO = "PassPacketInfo";
	public static final String PASSSECURITY = "PassSecurity";
	public static final String PATHEXISTS = "PathExists";
	public static final String PERMISSIONSSTARTONLY = "PermissionsStartOnly";
	public static final String PERSISTENT = "Persistent";
	public static final String PIDFILE = "PIDFile";
	public static final String PRIVATEDEVICES = "PrivateDevices";
	public static final String PRIVATEMOUNTS = "PrivateMounts";
	public static final String PRIVATENETWORK = "PrivateNetwork";
	public static final String PRIVATETMP = "PrivateTmp";
	public static final String PRIVATEUSERS = "PrivateUsers";
	public static final String PROCSUBSET = "ProcSubset";
	public static final String PROTECTCLOCK = "ProtectClock";
	public static final String PROTECTCONTROLGROUPS = "ProtectControlGroups";
	public static final String PROTECTHOME = "ProtectHome";
	public static final String PROTECTHOSTNAME = "ProtectHostname";
	public static final String PROTECTKERNELLOGS = "ProtectKernelLogs";
	public static final String PROTECTKERNELMODULES = "ProtectKernelModules";
	public static final String PROTECTKERNELTUNABLES = "ProtectKernelTunables";
	public static final String PROTECTPROC = "ProtectProc";
	public static final String PROTECTSYSTEM = "ProtectSystem";
	public static final String RANDOMIZEDDELAYSEC = "RandomizedDelaySec";
	public static final String READONLYDIRECTORIES = "ReadOnlyDirectories";
	public static final String READONLYPATHS = "ReadOnlyPaths";
	public static final String READWRITEDIRECTORIES = "ReadWriteDirectories";
	public static final String READWRITEPATHS = "ReadWritePaths";
	public static final String RECEIVEBUFFER = "ReceiveBuffer";
	public static final String REFUSEMANUALSTART = "RefuseManualStart";
	public static final String REFUSEMANUALSTOP = "RefuseManualStop";
	public static final String REMAINAFTEREXIT = "RemainAfterExit";
	public static final String REMOVEIPC = "RemoveIPC";
	public static final String REMOVEONSTOP = "RemoveOnStop";
	public static final String REQUIREDBY = "RequiredBy";
	public static final String REQUIRESMOUNTSFOR = "RequiresMountsFor";
	public static final String REQUIRES = "Requires";
	public static final String RESTARTFORCEEXITSTATUS = "RestartForceExitStatus";
	public static final String RESTARTKILLSIGNAL = "RestartKillSignal";
	public static final String RESTARTPREVENTEXITSTATUS = "RestartPreventExitStatus";
	public static final String RESTART = "Restart";
	public static final String RESTARTSEC = "RestartSec";
	public static final String RESTRICTADDRESSFAMILIES = "RestrictAddressFamilies";
	public static final String RESTRICTNAMESPACES = "RestrictNamespaces";
	public static final String RESTRICTREALTIME = "RestrictRealtime";
	public static final String RESTRICTSUIDSGID = "RestrictSUIDSGID";
	public static final String RUNTIMEDIRECTORYMODE = "RuntimeDirectoryMode";
	public static final String RUNTIMEDIRECTORYPRESERVE = "RuntimeDirectoryPreserve";
	public static final String RUNTIMEDIRECTORY = "RuntimeDirectory";
	public static final String RUNTIMEMAXSEC = "RuntimeMaxSec";
	public static final String SENDBUFFER = "SendBuffer";
	public static final String SENDSIGHUP = "SendSIGHUP";
	public static final String SENDSIGKILL = "SendSIGKILL";
	public static final String SERVICE = "Service";
	public static final String SLICE = "Slice";
	public static final String SOCKETGROUP = "SocketGroup";
	public static final String SOCKETMODE = "SocketMode";
	public static final String SOCKETS = "Sockets";
	public static final String SOCKETUSER = "SocketUser";
	public static final String STANDARDERROR = "StandardError";
	public static final String STANDARDINPUT = "StandardInput";
	public static final String STANDARDOUTPUT = "StandardOutput";
	public static final String STARTLIMITBURST = "StartLimitBurst";
	public static final String STARTLIMITINTERVALSEC = "StartLimitIntervalSec";
	public static final String STARTLIMITINTERVAL = "StartLimitInterval";
	public static final String STATEDIRECTORYMODE = "StateDirectoryMode";
	public static final String STATEDIRECTORY = "StateDirectory";
	public static final String STOPWHENUNNEEDED = "StopWhenUnneeded";
	public static final String SUCCESSACTION = "SuccessAction";
	public static final String SUCCESSEXITSTATUS = "SuccessExitStatus";
	public static final String SUPPLEMENTARYGROUPS = "SupplementaryGroups";
	public static final String SYMLINKS = "Symlinks";
	public static final String SYSLOGIDENTIFIER = "SyslogIdentifier";
	public static final String SYSTEMCALLARCHITECTURES = "SystemCallArchitectures";
	public static final String SYSTEMCALLERRORNUMBER = "SystemCallErrorNumber";
	public static final String SYSTEMCALLFILTER = "SystemCallFilter";
	public static final String TASKSMAX = "TasksMax";
	public static final String TIMEOUTSEC = "TimeoutSec";
	public static final String TIMEOUTSTARTSEC = "TimeoutStartSec";
	public static final String TIMEOUTSTOPSEC = "TimeoutStopSec";
	public static final String TIMESTAMPING = "Timestamping";
	public static final String TTYPATH = "TTYPath";
	public static final String TTYRESET = "TTYReset";
	public static final String TTYVHANGUP = "TTYVHangup";
	public static final String TTYVTDISALLOCATE = "TTYVTDisallocate";
	public static final String TYPE = "Type";
	public static final String UMASK = "UMask";
	public static final String UNIT = "Unit";
	public static final String UNSETENVIRONMENT = "UnsetEnvironment";
	public static final String USER = "User";
	public static final String UTMPIDENTIFIER = "UtmpIdentifier";
	public static final String WANTEDBY = "WantedBy";
	public static final String WANTS = "Wants";
	public static final String WATCHDOGSEC = "WatchdogSec";
	public static final String WHAT = "What";
	public static final String WHERE = "Where";
	public static final String WORKINGDIRECTORY = "WorkingDirectory";
	public static final String WRITABLE = "Writable";

	public static String addKeyTo(String key) {
		return key + "=";
	}

	public static String findKeyInClass(String youKey) {
		Class<SystDKeyMeta> systDKeyMetaCl = SystDKeyMeta.class;
		String youKeyUC = youKey.toUpperCase();
		Field[] thisclassFields = systDKeyMetaCl.getDeclaredFields();
		for (Field field : thisclassFields) {
			String fieldName = field.getName().toUpperCase();
			if (fieldName.equals(youKeyUC)) {
				try {
					String value = (String) field.get(null);
					if (!value.equalsIgnoreCase(fieldName)) {
						throw new UnsupportedOperationException(String.format(
								"исправить значение для ключа [%s] Field:[%s] в  SystDKeyMeta.findKeyInClass", youKey,
								fieldName));
					}
					return value;
				} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		throw new UnsupportedOperationException(String.format("Написать [%s] в  SystDKeyMeta.findKeyInClass", youKey));
	}

	private SystDKeyMeta() {
	}
}
