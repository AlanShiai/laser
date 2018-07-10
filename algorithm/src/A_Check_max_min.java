

import java.io.File;

public class A_Check_max_min {
	
	public static void main(String[] args) {
        
        double aDouble = 0.0, maxDouble = 0.0, minDouble = 0.0;
        
        int numBigOne=0, numNegative=0;
        
        File file;
        file = new File(".\\Sigma355.txt"); 
        file = new File(".\\Sigma532p.txt"); 
        file = new File(".\\Sigma532s.txt"); 
//        file = new File(".\\Sigma1064.txt"); 
        
		for (String line : ReadFile.readFile(file)) {
			aDouble = Double.parseDouble(line);
			if (aDouble > maxDouble) {
				maxDouble = aDouble;
			}
			if (aDouble < minDouble) {
				minDouble = aDouble;
			}
			if (aDouble > 0.001) {
				numBigOne ++;
			}
			if (aDouble < 0.0) {
				numNegative ++;
			}
		}
		System.out.println("maxDouble is : " + Double.toString(maxDouble));
		System.out.println("minDouble is : " + Double.toString(minDouble));
		System.out.println("1000 * maxDouble is : " + maxDouble * 1000);
		System.out.println("1000 * minDouble is : " + minDouble * 1000);

		// ignore it, first.
		System.out.println("numBigOne is : " + numBigOne);
		System.out.println("numNegative is : " + numNegative);
		
	}

}
