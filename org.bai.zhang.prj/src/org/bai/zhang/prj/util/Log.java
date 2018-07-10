package org.bai.zhang.prj.util;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public class Log {
	
	public final static File LOG_FOLDER;
	static {
		if (Platform.isRunning()) {
			LOG_FOLDER = Platform.getLocation().toFile();
		} else {
			LOG_FOLDER =  new File("c:\\00_laser_debug");
		}
	}


}
