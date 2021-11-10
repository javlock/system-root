package com.github.javlock.system.apidata.systemd.data;

import java.lang.reflect.Field;

public class SystDKeyMeta {

	public static final String Accept = "Accept";
	public static final String AccuracySec = "AccuracySec";
	public static final String After = "After";
	public static final String Alias = "Alias";
	public static final String AllowIsolate = "AllowIsolate";
	public static final String Also = "Also";
	public static final String AmbientCapabilities = "AmbientCapabilities";
	public static final String AssertPathExists = "AssertPathExists";
	public static final String Before = "Before";
	public static final String BindIPv6Only = "BindIPv6Only";
	public static final String BindsTo = "BindsTo";
	public static final String BusName = "BusName";
	public static final String CacheDirectory = "CacheDirectory";
	public static final String CapabilityBoundingSet = "CapabilityBoundingSet";
	public static final String ConditionACPower = "ConditionACPower";
	public static final String ConditionCapability = "ConditionCapability";
	public static final String ConditionControlGroupController = "ConditionControlGroupController";
	public static final String ConditionDirectoryNotEmpty = "ConditionDirectoryNotEmpty";
	public static final String ConditionFileIsExecutable = "ConditionFileIsExecutable";
	public static final String ConditionFileNotEmpty = "ConditionFileNotEmpty";
	public static final String ConditionFirstBoot = "ConditionFirstBoot";
	public static final String ConditionKernelCommandLine = "ConditionKernelCommandLine";
	public static final String ConditionNeedsUpdate = "ConditionNeedsUpdate";
	public static final String ConditionPathExists = "ConditionPathExists";
	public static final String ConditionPathExistsGlob = "ConditionPathExistsGlob";
	public static final String ConditionPathIsDirectory = "ConditionPathIsDirectory";
	public static final String ConditionPathIsMountPoint = "ConditionPathIsMountPoint";
	public static final String ConditionPathIsReadWrite = "ConditionPathIsReadWrite";
	public static final String ConditionPathIsSymbolicLink = "ConditionPathIsSymbolicLink";
	public static final String ConditionSecurity = "ConditionSecurity";
	public static final String ConditionVirtualization = "ConditionVirtualization";
	public static final String ConfigurationDirectory = "ConfigurationDirectory";
	public static final String ConfigurationDirectoryMode = "ConfigurationDirectoryMode";
	public static final String Conflicts = "Conflicts";
	public static final String CPUAccounting = "CPUAccounting";
	public static final String CPUSchedulingPolicy = "CPUSchedulingPolicy";
	public static final String CPUSchedulingPriority = "CPUSchedulingPriority";
	public static final String DefaultDependencies = "DefaultDependencies";
	public static final String DefaultInstance = "DefaultInstance";
	public static final String Delegate = "Delegate";
	public static final String Description = "Description";
	public static final String DeviceAllow = "DeviceAllow";
	public static final String DevicePolicy = "DevicePolicy";
	public static final String DirectoryNotEmpty = "DirectoryNotEmpty";
	public static final String Documentation = "Documentation";
	public static final String DynamicUser = "DynamicUser";
	public static final String Environment = "Environment";
	public static final String EnvironmentFile = "EnvironmentFile";
	public static final String ExecReload = "ExecReload";
	public static final String ExecStart = "ExecStart";
	public static final String ExecStartPost = "ExecStartPost";
	public static final String ExecStartPre = "ExecStartPre";
	public static final String ExecStop = "ExecStop";
	public static final String ExecStopPost = "ExecStopPost";
	public static final String FailureAction = "FailureAction";
	public static final String FileDescriptorName = "FileDescriptorName";
	public static final String FileDescriptorStoreMax = "FileDescriptorStoreMax";
	public static final String Group = "Group";
	public static final String IgnoreOnIsolate = "IgnoreOnIsolate";
	public static final String IgnoreSIGPIPE = "IgnoreSIGPIPE";
	public static final String IOSchedulingClass = "IOSchedulingClass";
	public static final String IOSchedulingPriority = "IOSchedulingPriority";
	public static final String IPAddressDeny = "IPAddressDeny";
	public static final String JobTimeoutAction = "JobTimeoutAction";
	public static final String JobTimeoutSec = "JobTimeoutSec";
	public static final String KeyringMode = "KeyringMode";
	public static final String KillMode = "KillMode";
	public static final String KillSignal = "KillSignal";
	public static final String LimitCORE = "LimitCORE";
	public static final String LimitMEMLOCK = "LimitMEMLOCK";
	public static final String LimitNOFILE = "LimitNOFILE";
	public static final String LimitNPROC = "LimitNPROC";
	public static final String LimitRTPRIO = "LimitRTPRIO";
	public static final String ListenDatagram = "ListenDatagram";
	public static final String ListenFIFO = "ListenFIFO";
	public static final String ListenNetlink = "ListenNetlink";
	public static final String ListenSequentialPacket = "ListenSequentialPacket";
	public static final String ListenSpecial = "ListenSpecial";
	public static final String ListenStream = "ListenStream";
	public static final String LoadCredential = "LoadCredential";
	public static final String LockPersonality = "LockPersonality";
	public static final String LogsDirectory = "LogsDirectory";
	public static final String LogsDirectoryMode = "LogsDirectoryMode";
	public static final String MakeDirectory = "MakeDirectory";
	public static final String MaxConnections = "MaxConnections";
	public static final String MemoryAccounting = "MemoryAccounting";
	public static final String MemoryDenyWriteExecute = "MemoryDenyWriteExecute";
	public static final String MemoryLow = "MemoryLow";
	public static final String MemoryMin = "MemoryMin";
	public static final String Nice = "Nice";
	public static final String NonBlocking = "NonBlocking";
	public static final String NoNewPrivileges = "NoNewPrivileges";
	public static final String NotifyAccess = "NotifyAccess";
	public static final String OnActiveSec = "OnActiveSec";
	public static final String OnBootSec = "OnBootSec";
	public static final String OnCalendar = "OnCalendar";
	public static final String OnFailureJobMode = "OnFailureJobMode";
	public static final String OnFailure = "OnFailure";
	public static final String OnUnitActiveSec = "OnUnitActiveSec";
	public static final String OOMScoreAdjust = "OOMScoreAdjust";
	public static final String Options = "Options";
	public static final String PAMName = "PAMName";
	public static final String PartOf = "PartOf";
	public static final String PassCredentials = "PassCredentials";
	public static final String PassPacketInfo = "PassPacketInfo";
	public static final String PassSecurity = "PassSecurity";
	public static final String PathExists = "PathExists";
	public static final String PermissionsStartOnly = "PermissionsStartOnly";
	public static final String Persistent = "Persistent";
	public static final String PIDFile = "PIDFile";
	public static final String PrivateDevices = "PrivateDevices";
	public static final String PrivateMounts = "PrivateMounts";
	public static final String PrivateNetwork = "PrivateNetwork";
	public static final String PrivateTmp = "PrivateTmp";
	public static final String PrivateUsers = "PrivateUsers";
	public static final String ProcSubset = "ProcSubset";
	public static final String ProtectClock = "ProtectClock";
	public static final String ProtectControlGroups = "ProtectControlGroups";
	public static final String ProtectHome = "ProtectHome";
	public static final String ProtectHostname = "ProtectHostname";
	public static final String ProtectKernelLogs = "ProtectKernelLogs";
	public static final String ProtectKernelModules = "ProtectKernelModules";
	public static final String ProtectKernelTunables = "ProtectKernelTunables";
	public static final String ProtectProc = "ProtectProc";
	public static final String ProtectSystem = "ProtectSystem";
	public static final String RandomizedDelaySec = "RandomizedDelaySec";
	public static final String ReadOnlyDirectories = "ReadOnlyDirectories";
	public static final String ReadOnlyPaths = "ReadOnlyPaths";
	public static final String ReadWriteDirectories = "ReadWriteDirectories";
	public static final String ReadWritePaths = "ReadWritePaths";
	public static final String ReceiveBuffer = "ReceiveBuffer";
	public static final String RefuseManualStart = "RefuseManualStart";
	public static final String RefuseManualStop = "RefuseManualStop";
	public static final String RemainAfterExit = "RemainAfterExit";
	public static final String RemoveIPC = "RemoveIPC";
	public static final String RemoveOnStop = "RemoveOnStop";
	public static final String RequiredBy = "RequiredBy";
	public static final String RequiresMountsFor = "RequiresMountsFor";
	public static final String Requires = "Requires";
	public static final String RestartForceExitStatus = "RestartForceExitStatus";
	public static final String RestartKillSignal = "RestartKillSignal";
	public static final String RestartPreventExitStatus = "RestartPreventExitStatus";
	public static final String Restart = "Restart";
	public static final String RestartSec = "RestartSec";
	public static final String RestrictAddressFamilies = "RestrictAddressFamilies";
	public static final String RestrictNamespaces = "RestrictNamespaces";
	public static final String RestrictRealtime = "RestrictRealtime";
	public static final String RestrictSUIDSGID = "RestrictSUIDSGID";
	public static final String RuntimeDirectoryMode = "RuntimeDirectoryMode";
	public static final String RuntimeDirectoryPreserve = "RuntimeDirectoryPreserve";
	public static final String RuntimeDirectory = "RuntimeDirectory";
	public static final String RuntimeMaxSec = "RuntimeMaxSec";
	public static final String SendBuffer = "SendBuffer";
	public static final String SendSIGHUP = "SendSIGHUP";
	public static final String SendSIGKILL = "SendSIGKILL";
	public static final String Service = "Service";
	public static final String Slice = "Slice";
	public static final String SocketGroup = "SocketGroup";
	public static final String SocketMode = "SocketMode";
	public static final String Sockets = "Sockets";
	public static final String SocketUser = "SocketUser";
	public static final String StandardError = "StandardError";
	public static final String StandardInput = "StandardInput";
	public static final String StandardOutput = "StandardOutput";
	public static final String StartLimitBurst = "StartLimitBurst";
	public static final String StartLimitIntervalSec = "StartLimitIntervalSec";
	public static final String StartLimitInterval = "StartLimitInterval";
	public static final String StateDirectoryMode = "StateDirectoryMode";
	public static final String StateDirectory = "StateDirectory";
	public static final String StopWhenUnneeded = "StopWhenUnneeded";
	public static final String SuccessAction = "SuccessAction";
	public static final String SuccessExitStatus = "SuccessExitStatus";
	public static final String SupplementaryGroups = "SupplementaryGroups";
	public static final String Symlinks = "Symlinks";
	public static final String SyslogIdentifier = "SyslogIdentifier";
	public static final String SystemCallArchitectures = "SystemCallArchitectures";
	public static final String SystemCallErrorNumber = "SystemCallErrorNumber";
	public static final String SystemCallFilter = "SystemCallFilter";
	public static final String TasksMax = "TasksMax";
	public static final String TimeoutSec = "TimeoutSec";
	public static final String TimeoutStartSec = "TimeoutStartSec";
	public static final String TimeoutStopSec = "TimeoutStopSec";
	public static final String Timestamping = "Timestamping";
	public static final String TTYPath = "TTYPath";
	public static final String TTYReset = "TTYReset";
	public static final String TTYVHangup = "TTYVHangup";
	public static final String TTYVTDisallocate = "TTYVTDisallocate";
	public static final String Type = "Type";
	public static final String UMask = "UMask";
	public static final String Unit = "Unit";
	public static final String UnsetEnvironment = "UnsetEnvironment";
	public static final String User = "User";
	public static final String UtmpIdentifier = "UtmpIdentifier";
	public static final String WANTEDBY = "WantedBy";
	public static final String Wants = "Wants";
	public static final String WatchdogSec = "WatchdogSec";
	public static final String What = "What";
	public static final String Where = "Where";
	public static final String WorkingDirectory = "WorkingDirectory";
	public static final String Writable = "Writable";

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
