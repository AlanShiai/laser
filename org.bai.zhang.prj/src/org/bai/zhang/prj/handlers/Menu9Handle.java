package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.dialog.UpdateDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class Menu9Handle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		new UpdateDialog(Display.getDefault().getActiveShell()).open();

		return null;
	}
	
}
