package org.zhangbai.display.tst;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TstDrawDisplay2 {
	
	/*
	 * wave #1
	 * **********************************************
	 *   /\  /\  /\  /\  /\  /\  /\  /\  /\  /\  /\  /
	 *  /  \/  \/  \/  \/  \/  \/  \/  \/  \/  \/  \/ 
	 * **********************************************
	 */
	private static int[] wave1_data = new int[]{-100, 0, 100, 0, };
	/*
	 * wave #2
	 * **********************************************
	 *    _     _     _     _     _     _     _     _
	 *   / \   / \   / \   / \   / \   / \   / \   / 
	 *  /   \_/   \_/   \_/   \_/   \_/   \_/   \_/  
	 * **********************************************
	 */
	private static int[] wave2_data = new int[]{-100, 0, 100, 100, 0, -100 };
	/*
	 * wave #3
	 * **********************************************
	 *     _       _       _       _       _       _  
	 *   _/ \_   _/ \_   _/ \_   _/ \_   _/ \_   _/ \_
	 *  /     \_/     \_/     \_/     \_/     \_/     \
	 * **********************************************
	 */
	private static int[] wave3_data = new int[]{-100, 0, 0, 100, 100, 0, 0, -100, -100, 0, 0, 100, 100, 0, 0, -100,};
	/*
	 * wave #4
	 * **********************************************
	 *     /\          /\          /\          /\
	 *    /  \    /\  /  \    /\  /  \    /\  /  \
	 *   /    \  /  \/    \  /  \
	 *   /    \  /  \/    \  /   
	 *  /      \/          \/          \/          \/  
	 * **********************************************
	 */
	private static int[] wave4_data = new int[]{-200, -100, 0, 100, 200, 100, 0, -100, -200, -100, 0, 100, 0, -100, 0, 100, 200, 100, 0, -100 };

	private static int timerCounter = 0;

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		shell.addListener(SWT.Paint, event -> {
			GC gc = event.gc;

			int x = event.getBounds().x, y = event.getBounds().y, width = event.getBounds().width, height = event.getBounds().height;

			/*
			 * draw x axis.
			 */
			gc.setLineWidth(3);
			gc.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
			gc.drawLine(x, y + height/2, x + width, y + height/2);

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

			int xSum=0;
			for (int i = 0; i*60 < width; i++) {
				gc.drawLine(x+60*i, y, x+60*i, y + height);
				xSum = i;
			}

			/*
			 * draw y axis
			 */
			gc.setLineWidth(3);
			gc.setForeground(display.getSystemColor(SWT.COLOR_YELLOW));
			gc.drawLine(x+60*(xSum/2+1), y, x+60*(xSum/2+1), y + height);

			System.out.println(xSum);


			/*
			 * draw wave
			 */
			gc.setLineWidth(3);
			gc.setLineStyle(SWT.LINE_SOLID);
			gc.setForeground(display.getSystemColor(SWT.COLOR_GREEN));

			List<Integer> list = new ArrayList<Integer>();
			//			for(int i = 0; i <= xSum + 1; i++) {
			//				list.add(x+60*i);
			//				if( i % 2 == 0)
			//					list.add(y + height/2 + 120);
			//				else
			//					list.add(y + height/2 - 120);
			//			}

			for(int i = 0; i <= xSum + 1; i++) {
				list.add(0);
				list.add(0);
			}
			
			

			for(int i = 0; i <= xSum + 1; i++) {
				//				list.add(x+60*i);
				list.set(2*i, x+60*i);
			}
			
//			for(int i = 0; i <= xSum + 1; i++) {
//				if(timerCounter % 2 == 0)
//					if( i % 2 == 0)
//						list.set(2*i+1, y + height/2 + 120);
//					else
//						list.set(2*i+1, y + height/2 - 120);
//				else
//					if( i % 2 == 0)
//						list.set(2*i+1, y + height/2 - 120);
//					else
//						list.set(2*i+1, y + height/2 + 120);
//					
//			}
			
			int wave_start_index = timerCounter % (wave1_data.length); 
			for(int i = 0; i <= xSum + 1; i++) {
//				list.set(2*i+1, y + height/2 + wave1_data[(wave_start_index+i) % wave1_data.length]);
//				list.set(2*i+1, y + height/2 + wave2_data[(wave_start_index+i) % wave2_data.length]);
//				list.set(2*i+1, y + height/2 + wave3_data[(wave_start_index+i) % wave3_data.length]);
				list.set(2*i+1, y + height/2 + wave4_data[(wave_start_index+i) % wave4_data.length]);
			}

//			int[] points = new int[list.size()];
//			for(int i = 0 ; i < points.length; i++)
//				points[i] = list.get(i);
//			gc.drawPolyline(points);
//			
			int type = timerCounter % 5;
			if ( 0 == type) {
				
				gc.drawRectangle(x + 400, y+ 200, width-200, height-200);
			}
			if ( 1 == type) {
				gc.drawRectangle(x + 300, y + 200, width-200, height-200);
			}
			if ( 2 == type) {
				gc.drawRectangle(x + 200, y + 200, width-200, height-200);
			}
			if ( 3 == type) {
				gc.drawRectangle(x + 100, y + 200, width-200, height-200);
			}
			if ( 4 == type) {
				gc.drawRectangle(x + 0, y + 200, width-200, height-200);
			}

		});

		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(timerCounter ++);
				display.syncExec(new Runnable() {
					public void run() {
						if ( null != shell && !shell.isDisposed() )
							shell.redraw();
							;
					}
				});
			}

		}, 200, 300);

		shell.setSize(600, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}


}
