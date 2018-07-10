package org.bai.zhang.prj.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class HistoryDialog extends Dialog {

	public HistoryDialog(Shell parentShell) {
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
		
		Combo yearCombo = new Combo(composite, SWT.BORDER);
		yearCombo.setItems(new String[]{"2016","2015","2014"});
		yearCombo.select(0);
		yearCombo.setLayoutData(new GridData());
		
		Table table = new Table (composite, SWT.MULTI | SWT.BORDER);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		
		String[] titles = {"导入数据时间", "数据起始时间", "数据结束时间", "查看导入数据"};
		for (int i=0; i< titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			column.setWidth(120);
		}
		
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText (0, "2016/09/12/14:30");		item.setText (1, "2016/09/11/11:30");
		item.setText (2, "2016/09/11/16:30");		item.setText (3, "View It");
		
		item = new TableItem (table, SWT.NONE);
		item.setText (0, "2016/09/12/14:30");		item.setText (1, "2016/09/10/10:30");
		item.setText (2, "2016/09/19/16:30");		item.setText (3, "View It");
		
		item = new TableItem (table, SWT.NONE);
		item.setText (0, "2016/09/12/14:30");		item.setText (1, "2016/09/09/14:20");
		item.setText (2, "2016/09/09/16:30");		item.setText (3, "View It");
		
		item = new TableItem (table, SWT.NONE);
		item.setText (0, "2016/09/12/14:30");		item.setText (1, "2016/09/08/14:10");
		item.setText (2, "2016/09/08/16:30");		item.setText (3, "View It");
		
		item = new TableItem (table, SWT.NONE);
		item.setText (0, "2016/09/12/14:30");		item.setText (1, "2016/09/07/14:40");
		item.setText (2, "2016/09/07/16:30");		item.setText (3, "View It");
		
		
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 500;
		data.heightHint = 200;
		table.setLayoutData(data);
		
		return composite;
	}
	
	@Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("历史查询");
    }
	
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE;
	}

}
