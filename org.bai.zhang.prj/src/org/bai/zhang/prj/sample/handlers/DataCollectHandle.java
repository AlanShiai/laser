package org.bai.zhang.prj.sample.handlers;

import org.bai.zhang.prj.sample.dialog.DataCollectDialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class DataCollectHandle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		new DataCollectDialog(Display.getDefault().getActiveShell()).open();
		return null;
	}
}
