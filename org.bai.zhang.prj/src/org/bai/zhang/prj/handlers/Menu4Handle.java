package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.dialog.HistoryDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class Menu4Handle extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute  xxx");
		new HistoryDialog(Display.getDefault().getActiveShell()).open();

		return null;
	}
	
}
