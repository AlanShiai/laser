package org.bai.zhang.prj.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SettingDialog extends Dialog {

	public SettingDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		
		Group group1 = new Group(composite, SWT.NONE);
		group1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group1.setText("��Զ���Ƕ�����");
		createGroup1Area(group1);
		
		Group group2 = new Group(composite, SWT.NONE);
		group2.setLayoutData(new GridData(GridData.FILL_BOTH));
		group2.setText("ʾ��������");
		createGroup2Area(group2);
		
		return composite;
	}
	
    private void createGroup1Area(Composite parent) {
    	parent.setLayout(new GridLayout(2, false));
    	
    	createLabelText(parent, "ˮƽ�����ã�0-180����");
    	createLabelText(parent, "���������ã�0-90����");
	}
    
    private void createLabelText(Composite parent, String LabelText) {
    	Label lable = new Label(parent, SWT.NONE);
    	lable.setText(LabelText);
    	
    	Text text = new Text(parent, SWT.NONE);
    	text.setText("");
    	text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createGroup2Area(Composite parent) {
    	parent.setLayout(new GridLayout(2, false));
    	
    	createLabelText(parent, "355�����������ã�");
    	createLabelText(parent, "355�����������ã�");
    	createLabelText(parent, "532�����������ã�");
    	createLabelText(parent, "532�����������ã�");
    	createLabelText(parent, "1064�����������ã�");
    	createLabelText(parent, "1064�����������ã�");
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(
				Math.max(600, super.getInitialSize().x), 
				Math.max(500, super.getInitialSize().y));
	}

	@Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("��������");
    }
	
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

}
