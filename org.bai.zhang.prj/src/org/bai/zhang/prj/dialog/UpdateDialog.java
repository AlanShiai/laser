package org.bai.zhang.prj.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UpdateDialog extends Dialog {

	public UpdateDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Browser browser = new Browser(parent, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 600;
		gridData.heightHint = 400;
		browser.setLayoutData(gridData);

		browser.setUrl(UpdateDialog.class.getResource("update.html").toString());
		
//		try {
//			System.out.println(Activator.getDefault().getBundle().getLocation());
//
//			System.out.println(FileLocator.getBundleFile(Activator.getDefault().getBundle()).toPath());
//			System.out.println(FileLocator.getBundleFile(Activator.getDefault().getBundle()).toPath().resolve("update.html"));
//			System.out.println(FileLocator.getBundleFile(Activator.getDefault().getBundle()).toPath());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//		try {
//						browser.setUrl(FileLocator.resolve(UpdateDialog.class.getResource("update.html")).toString());
//			browser.setUrl(FileLocator.getBundleFile(Activator.getDefault().getBundle()).toPath().resolve("update.html").toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		

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
		shell.setText("软件更新历史");
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

	public static void main(String[] args) {
		new UpdateDialog(Display.getDefault().getActiveShell()).open();
	}

}
