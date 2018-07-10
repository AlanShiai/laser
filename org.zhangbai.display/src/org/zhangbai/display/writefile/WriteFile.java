package org.zhangbai.display.writefile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	
	private final static File LOG_FOLDER = new File("logs");
	
	private final static File LOG_FILE = new File(LOG_FOLDER, "serial_command_history.log");
	
	private final static boolean APPEND_TRUE = true;
	
	public static void noteSerialCommand(String serialCommand) {
		try {
			FileWriter writer = new FileWriter(LOG_FILE, APPEND_TRUE);
			writer.append(MyDate.getDateString());
			writer.append(serialCommand);
			writer.append("\n");
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		noteSerialCommand("tst");
	}

}
