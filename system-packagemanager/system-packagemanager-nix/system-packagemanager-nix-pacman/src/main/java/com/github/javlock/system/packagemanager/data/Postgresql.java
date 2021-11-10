package com.github.javlock.system.packagemanager.data;

import com.github.javlock.system.packagemanager.nix.linux.pacman.PacmanManager;

public class Postgresql extends ProgrammImpl {

	@Override
	public String getName() {

		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean install() {
		PacmanManager.getInstance().install(this);

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unInstall() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
}
