package org.bai.zhang.prj.sample.dialog;

import gnu.io.SerialPort;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SerialDebugDialog extends Dialog {

	public SerialDebugDialog(Shell parentShell) {
		super(parentShell);
	}

	Combo serialPortCombo;
	Text serialCmd;
	@Override
	protected Control createDialogArea(Composite parent) {
		serialPortCombo = new Combo(parent, SWT.READ_ONLY);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		serialPortCombo.setLayoutData(gridData);
		serialPortCombo.setItems(new String[] {
				"COM0", "COM1", "COM2", "COM3", "COM4",
				"COM5", "COM6", "COM7", "COM8", "COM9",
		});
		serialPortCombo.setText("COM0");
		
		serialCmd = new Text(parent, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 300;
//		gridData.heightHint = 400;
		serialCmd.setLayoutData(gridData);
		
		Button send = new Button(parent, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		send.setLayoutData(gridData);
		send.setText("Send");
		send.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("send widgetSelected");
//				Activator.getDefault().log("send widgetSelected");
				System.out.println(SerialTool.findPort());

				SerialPort serialPort = SerialTool.openPort(serialPortCombo.getText(), 9600);
				
				System.out.println(serialCmd.getText());
				SerialTool.sendToPort(serialPort, serialCmd.getText());

				SerialTool.closePort(serialPort);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
//				Activator.getDefault().log("send widgetDefaultSelected");
			}
			
		});

		return parent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("serial debug dialog");
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

	public static void main(String[] args) {
		new SerialDebugDialog(Display.getDefault().getActiveShell()).open();
	}

}
