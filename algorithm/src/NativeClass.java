
public class NativeClass {
	// 声明本地方法  

		public native int openDevice();
		
		public native void collectBlockEts();

		public native void collectBlockImmediate();

		public native void collectBlockTriggered();

		public native void collectRapidBlock();

		public native void collectStreamingImmediate();

		public native void collectStreamingTriggered();

		public native void closeDevice();

		
		public native short[] collectBlockEtsArray();

		public native short[] collectBlockImmediateArray();

		public native short[] collectBlockTriggeredArray();

		public native short[] collectRapidBlockArray();

		public native void collectStreamingImmediateArray();

		public native short[] collectStreamingTriggeredArray();
		
		public native short[] collectArray();
		/*
	    public static void main(String[] args) {  
	    // 加载动态链接库  
	    	double[] result=new double[36000];
	          System.loadLibrary("DataCollectLib");  
	          NativeClass nativeCode = new NativeClass();  
	          nativeCode.openDevice(); 
	          result=nativeCode.collectBlockTriggeredArray();
	          nativeCode.closeDevice();
	    }
	    */
}
