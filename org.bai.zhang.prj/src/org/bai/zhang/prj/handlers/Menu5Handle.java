package org.bai.zhang.prj.handlers;

import org.bai.zhang.prj.nls.Messages;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class Menu5Handle extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println( getClass() + " execute");
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), getClass() + " execute", Messages.menu5);

		return null;
	}
}
