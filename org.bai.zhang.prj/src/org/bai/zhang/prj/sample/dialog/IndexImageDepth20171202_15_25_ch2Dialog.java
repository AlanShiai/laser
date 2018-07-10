package org.bai.zhang.prj.sample.dialog;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class IndexImageDepth20171202_15_25_ch2Dialog extends LidarDialog {

	public IndexImageDepth20171202_15_25_ch2Dialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Image getLidarImage() {
		return IndexImageDepth20171202_15_25_ch2.createIndexImage();
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("20171202_15_25_ch2 Sample");
	}

	public static void main(String[] args) {
		new IndexImageDepth20171202_15_25_ch2Dialog(Display.getDefault().getActiveShell()).open();
	}

}
