package com.github.javlock.system.kernel.init;

import com.github.javlock.system.kernel.Kernel;

public class KernelInit {

	public static void main(String[] args) {
		Kernel kernel = new Kernel();
		kernel.init();
		kernel.start();
	}
}
