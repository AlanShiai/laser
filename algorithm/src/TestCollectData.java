import java.io.File;


public class TestCollectData {

	public static void main(String[] args) {
        System.loadLibrary("DataCollectLib");  
        NativeClass nativeCode = new NativeClass();  
        nativeCode.openDevice(); 
        
        String s=new String(".\\block.txt");
        File file = new File(s);
        File blockDir = new File(file.getParent(), "measureData");
        System.out.println("Sava path: " + blockDir.getAbsolutePath());
        blockDir.mkdir();
        int i = 10;
        while ( i -- > 0) {
        	nativeCode.collectBlockTriggered();
        	if (file.exists()) {
        		File renameFile = new File(blockDir, System.currentTimeMillis() + "");
        		System.out.println("rename : " + renameFile.getAbsolutePath());
        		file.renameTo(renameFile);
        	}
        }
        nativeCode.closeDevice();
        
		System.out.println("finish");
	}

}
