package org.zhangbai.display.index;

import java.math.BigDecimal;
import java.math.BigInteger;

public class A_tst {
	
	public static void main(String[] args) {
//		System.out.println(Byte.parseByte("255", 16));
		System.out.println(Byte.valueOf("-1"));
		System.out.println(Byte.toUnsignedInt(Byte.valueOf("-1")));
		System.out.println(Byte.toUnsignedInt(Byte.parseByte("-1")));
		System.out.println(Integer.parseUnsignedInt("255"));
		System.out.println((byte) Integer.parseUnsignedInt("255"));
		System.out.println((byte) 255);
		
//		System.out.println(Integer.parseUnsignedInt("1.04E+04"));
		
		
		

        BigDecimal bd = new BigDecimal("1.04E+04");  
        System.out.println(bd.toPlainString());
        System.out.println(bd.toBigInteger());
        System.out.println(bd.toBigInteger().intValue());
        
        double aDouble = 0.0, maxDouble = 0.0, minDouble = 0.0;
        
        int numBigOne=0, numNegative=0;
        
		for (String line : ReadFile.readFile("Sigma355.txt")) {
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
		
		double c=3.0e8;
		
		System.out.println("Distance 0 : " + 8e-9*0*c/2);
		System.out.println("Distance 1 : " + 8e-9*1*c/2);
		System.out.println("Distance 2 : " + 8e-9*9999*c/2);
		
//		d = 12000*12000*5000;
		System.out.println("Distance 2 : " + 12000*12000*1000);
		System.out.println("Distance 2 : " + 12000*12000);
		
		
		
		System.out.println("tst 2 : " + 1023/8);
		System.out.println("tst 3 : " + 1023%8);
        
//        BigInteger bi = new BigInteger("1.04E+04");
//        System.out.println(bi.intValue());
        
        double d = (2 * 255.0)/233;
        byte i = (byte) d;
	}

}
