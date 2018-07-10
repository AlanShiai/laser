package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.View;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class Menu3Handle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
//		MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.menu3);

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(View.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
