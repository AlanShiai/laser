package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.view.SettingView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class Menu1Handle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		
//		new SettingDialog(Display.getDefault().getActiveShell()).open();
		
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SettingView.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.menu1);
		
		return null;
	}

}
