package org.bai.zhang.prj.sample.handlers;

import org.bai.zhang.prj.sample.dialog.IndexImageDepth20171202_19_56_ch2Dailog;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;

public class Sample20171202_19_56_ch2Handle  extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		new IndexImageDepth20171202_19_56_ch2Dailog(Display.getDefault().getActiveShell()).open();
		return null;
	}
}
