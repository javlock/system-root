package com.github.javlock.system.apidata.systemd.data;

public class ServicesDemoManual {

	public static final String UPDATERSERVICEDATA = "[Unit]\n" + "Description=JavLock-System-Updater \n"
			+ "Wants=network.target\n" + "After=network.target\n" + "\n" + "[Service]\n" + "User=root\n"
			+ "Group=root\n" + "\n" + "WorkingDirectory=/tmp/\n"
			+ "ExecStart=bash -c \"java -jar /opt/javlock-system/system-updater/target/system-updater-*-git-*-jar-with-dependencies.jar\"\n"
			+ "\n" + "Restart=always\n" + "\n" + "[Install]\n" + "WantedBy=multi-user.target\n" + "";

}
