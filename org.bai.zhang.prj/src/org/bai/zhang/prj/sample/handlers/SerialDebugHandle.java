package org.bai.zhang.prj.sample.handlers;

import org.bai.zhang.prj.sample.dialog.SerialDebugDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class SerialDebugHandle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		new SerialDebugDialog(Display.getDefault().getActiveShell()).open();
		return null;
	}
}
