package org.zhangbai.display.tst;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TstDrawDisplay {

	
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		shell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		shell.addListener(SWT.Paint, event -> {
			GC gc = event.gc;
			
//			gc.setLineAttributes(new LineAttributes(10, SWT.CAP_FLAT, SWT.JOIN_MITER, SWT.LINE_SOLID, null, 0, 10));
//			gc.drawPolyline(new int[]{50, 100, 50, 20, 60, 30, 50, 45});
//
//			gc.setLineAttributes(new LineAttributes(1/2f, SWT.CAP_FLAT, SWT.JOIN_MITER, SWT.LINE_DOT, null, 0, 10));
//			gc.drawPolyline(new int[]{100, 100, 100, 20, 110, 30, 100, 45});
			
			int x = event.getBounds().x, y = event.getBounds().y, width = event.getBounds().width, height = event.getBounds().height;
			
			/*
			 * draw x y axis.
			 */
			gc.setLineWidth(3);
			gc.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
			gc.drawLine(x, y + height/2, x + width, y + height/2);
			gc.drawLine(x+width/2, y, x + width/2, y + height);
			
			/*
			 * draw x coordinate.
			 */
			gc.setLineWidth(1);
			gc.setLineStyle(SWT.LINE_DOT);
			gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_YELLOW));
			for (int i = 0 ; (y + height/2 - 40*i) > y ; i++) {
				gc.drawLine(x, y + height/2 - 40*i, x + width, y + height/2 - 40*i);
				gc.drawLine(x, y + height/2 + 40*i, x + width, y + height/2 + 40*i);
				
			}
			/*
			 * draw y coordinate.
			 */
			for (int i = 0 ; (x+width/2 - 60*i) > x ; i++) {
//				gc.drawLine(x, y + height/2 - 40*i, x + width, y + height/2 - 40*i);
				gc.drawLine(x+width/2 - 60*i, y, x + width/2 - 60*i, y + height);
				gc.drawLine(x+width/2 + 60*i, y, x + width/2 + 60*i, y + height);
			}

			/*
			 * draw y coordinate.
			 */
			gc.setLineWidth(3);
			gc.setLineStyle(SWT.LINE_SOLID);
			gc.setForeground(display.getSystemColor(SWT.COLOR_GREEN));
			
			List<Integer> list = new ArrayList<Integer>();
			list.add(x); list.add(y + height/2);
			list.add(x + 50); list.add(y + height/2 + 50);
			list.add(x+width/2); list.add(y + 50);
			list.add(x + width); list.add(y + height/2);
			
			int[] points = new int[list.size()];
			for(int i = 0 ; i < points.length; i++)
				points[i] = list.get(i);
			gc.drawPolyline(points);
			
//			gc.drawPolyline(new int[]{x, y + height/2, x+ 50, y + height/2 + 50, x+width/2, y+40, x + width, y + height/2});
			
			
		});

		shell.setSize(600, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	
}
