package org.bai.zhang.prj.sample.dialog;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LidarSmoothDialog extends LidarDialog {

	public LidarSmoothDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Image getLidarImage() {
		return IndexImageDepth8_4_lidar_width_1536.createIndexImage();
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Lidar Smooth Sample");
	}

	public static void main(String[] args) {
		new LidarSmoothDialog(Display.getDefault().getActiveShell()).open();
	}

}
