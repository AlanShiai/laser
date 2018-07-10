package org.bai.zhang.prj.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bai.zhang.prj.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class RealtimeMeasureView extends ViewPart {
	public static final String ID = "org.bai.zhang.prj.view.RealtimeMeasureView";

	private Image backgroundImage = Activator.getImageDescriptor("icons/realtime_measure.png").createImage();

	@Override
	public void createPartControl(Composite parent) {

		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		//		Label label = new Label(composite, SWT.BORDER);
		//		label.setImage(Activator.getImageDescriptor("icons/realtime_measure.png").createImage());
		//		label.setBackgroundImage(Activator.getImageDescriptor("icons/realtime_measure.png").createImage());
		//		label.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite label = new Composite(composite, SWT.BORDER);
		//		label.setBackgroundImage(Activator.getImageDescriptor("icons/realtime_measure.png").createImage());
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.widthHint = 400;
		label.setLayoutData(gridData);

		label.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Image tmpImage;

				ImageData data = backgroundImage.getImageData().scaledTo(e.width, e.height);
				tmpImage = new Image(e.display, data);
				e.gc.drawImage(tmpImage, 0, 0);

				if(null != tmpImage && !tmpImage.isDisposed()) {
					tmpImage.dispose();
				}
			}
		});

		Composite displayCompostie = new Composite(composite, SWT.BORDER);
		displayCompostie.setLayoutData(new GridData(GridData.FILL_BOTH));

		createArea4Display4(displayCompostie);
	}

	private void createArea4Display4(Composite parent) {
		parent.setLayout(new GridLayout(2, true));

		Label label1 = new Label(parent, SWT.NONE);
		label1.setText("355nm波长实时测量曲线");

		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("532(s)nm波长实时测量曲线");

		Label label11 = new Label(parent, SWT.BORDER);
		Label label22 = new Label(parent, SWT.BORDER);


		Label label3 = new Label(parent, SWT.NONE);
		label3.setText("532(p)nm波长实时测量曲线");

		Label label4 = new Label(parent, SWT.NONE);
		label4.setText("1064nm波长实时测量曲线");

		Label label33 = new Label(parent, SWT.BORDER);
		Label label44 = new Label(parent, SWT.BORDER);

		label11.setLayoutData(new GridData(GridData.FILL_BOTH));
		label22.setLayoutData(new GridData(GridData.FILL_BOTH));
		label33.setLayoutData(new GridData(GridData.FILL_BOTH));
		label44.setLayoutData(new GridData(GridData.FILL_BOTH));

		createDynamicWave(label11, WaveType.WAVE1);
		createDynamicWave(label22, WaveType.WAVE2);
		createDynamicWave(label33, WaveType.WAVE3);
		createDynamicWave(label44, WaveType.WAVE4);
	}

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
	 *   /    \  /  \/    \  /  \/    \  /  \/    \  /   
	 *  /      \/          \/          \/          \/  
	 * **********************************************
	 */
	private static int[] wave4_data = new int[]{-200, -100, 0, 100, 200, 100, 0, -100, -200, -100, 0, 100, 0, -100, 0, 100, 200, 100, 0, -100 };

	private static int timerCounter1 = 0;
	private static int timerCounter2 = 0;
	private static int timerCounter3 = 0;
	private static int timerCounter4 = 0;

	private enum WaveType {WAVE1, WAVE2, WAVE3, WAVE4};

	private void createDynamicWave(final Label label, final WaveType waveType) {

		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				switch (waveType) {
				case WAVE1: timerCounter1 ++; break;
				case WAVE2: timerCounter2 ++; break;
				case WAVE3: timerCounter3 ++; break;
				case WAVE4: timerCounter4 ++; break;
				default: ;
				}
				if ( null != label && !label.isDisposed() ) {
					label.getDisplay().syncExec(new Runnable() {
						public void run() {
							if ( null != label && !label.isDisposed() )
								label.redraw();
							;
						}
					});
				}
			}

		}, 200, 300);

		label.setBackground(label.getDisplay().getSystemColor(SWT.COLOR_BLACK));

		label.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				GC gc = e.gc;

				int x = e.x, y = e.y, width = e.width, height = e.height;

				/*
				 * draw x axis.
				 */
				gc.setLineWidth(3);
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_YELLOW));
				gc.drawLine(x, y + height/2, x + width, y + height/2);

				/*
				 * draw x coordinate.
				 */
				gc.setLineWidth(1);
				gc.setLineStyle(SWT.LINE_DOT);
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_YELLOW));
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
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_YELLOW));
				gc.drawLine(x+60*(xSum/2+1), y, x+60*(xSum/2+1), y + height);

				/*
				 * draw wave
				 */
				gc.setLineWidth(3);
				gc.setLineStyle(SWT.LINE_SOLID);
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_GREEN));

				List<Integer> list = new ArrayList<Integer>();

				for(int i = 0; i <= xSum + 1; i++) {
					list.add(0);
					list.add(0);
				}

				for(int i = 0; i <= xSum + 1; i++) {
					list.set(2*i, x+60*i);
				}

				int wave_start_index = 0;
				switch (waveType) {
				case WAVE1: wave_start_index = timerCounter1 % (wave1_data.length); break;
				case WAVE2: wave_start_index = timerCounter2 % (wave1_data.length); break;
				case WAVE3: wave_start_index = timerCounter3 % (wave1_data.length); break;
				case WAVE4: wave_start_index = timerCounter4 % (wave1_data.length); break;
				default: ;
				}

				for(int i = 0; i <= xSum + 1; i++) {
					switch (waveType) {
					case WAVE1: list.set(2*i+1, y + height/2 + wave1_data[(wave_start_index+i) % wave1_data.length]); break;
					case WAVE2: list.set(2*i+1, y + height/2 + wave2_data[(wave_start_index+i) % wave2_data.length]); break;
					case WAVE3: list.set(2*i+1, y + height/2 + wave3_data[(wave_start_index+i) % wave3_data.length]); break;
					case WAVE4: list.set(2*i+1, y + height/2 + wave4_data[(wave_start_index+i) % wave4_data.length]); break;
					default: ;
					}				}


				int[] points = new int[list.size()];
				for(int i = 0 ; i < points.length; i++)
					points[i] = list.get(i);
				gc.drawPolyline(points);

			}

		});
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if(null != backgroundImage && !backgroundImage.isDisposed()) {
			backgroundImage.isDisposed();
		}
		super.dispose();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
