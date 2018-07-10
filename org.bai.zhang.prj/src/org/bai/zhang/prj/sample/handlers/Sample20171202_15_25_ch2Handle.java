package org.bai.zhang.prj.sample.handlers;

import org.bai.zhang.prj.sample.dialog.IndexImageDepth20171202_15_25_ch2Dialog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class Sample20171202_15_25_ch2Handle extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		new IndexImageDepth20171202_15_25_ch2Dialog(Display.getDefault().getActiveShell()).open();
		return null;
	}
}
