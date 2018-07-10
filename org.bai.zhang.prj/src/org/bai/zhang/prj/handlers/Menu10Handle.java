package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.view.TimingMeasureView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class Menu10Handle  extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TimingMeasureView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
