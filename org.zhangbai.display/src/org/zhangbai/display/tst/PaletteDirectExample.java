package org.zhangbai.display.tst;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PaletteDirectExample {

    static Display display;

    public Image createIndexImage() {
        // ***                    PaletteData(redMask, greenMask, blueMask)
        PaletteData palette = new PaletteData(0xFF0000   , 0xFF00   , 0xFF);

        ImageData imageData = new ImageData(48,48,24,palette);
        for (int x=0;x<48;x++) {
            for(int y=0;y<48;y++) {
                if(y > 11 && y < 35 && x > 11 && x < 35) {
                    imageData.setPixel(x,y,0xdd3311);   // Set the center to red
                } else {
                    imageData.setPixel(x,y,0x33dd22);   // and everything else to green
                }
            }
        };
        return new Image(display,imageData);
    }

    private static void lightImage(ImageData imageData) {
        int pixel;
        RGB rgb;
        Float h,s,b;
        for (int x=0;x<48;x++) {
            for(int y=0;y<48;y++) {
                pixel = imageData.getPixel(x,y);
                rgb = imageData.palette.getRGB(pixel);
                System.out.println(rgb);
                if(rgb.getHSB()[1] != 0.0) {
                    s = rgb.getHSB()[1]+(1-1*rgb.getHSB()[1])/2;
                    b = rgb.getHSB()[2]+(1-1*rgb.getHSB()[2])/3;
                    rgb = new RGB(rgb.getHSB()[0],s,b);
                    pixel = imageData.palette.getPixel(rgb);
                    System.out.println("-- " +rgb);
                    imageData.setPixel(x, y, pixel);
                }
            }
        };
    }

    public static void main(String[] args) {
        display = new Display();
        PaletteDirectExample paletteDirectExample = new PaletteDirectExample();
        Image image = paletteDirectExample.createIndexImage();
        
    	Shell shell = new Shell(display);
    	// draw the results on the shell
    	shell.addPaintListener(e -> {
    		e.gc.drawText("Original Image:", 10, 10, true);
    		e.gc.drawImage(image, 10, 40);
    	});
    	shell.setSize(300, 300);
    	shell.open();
    	while (!shell.isDisposed()) {
    		if (!display.readAndDispatch())
    			display.sleep();
    	}
        
        lightImage(image.getImageData());
        
        image.dispose();
    	display.dispose();

    }

}