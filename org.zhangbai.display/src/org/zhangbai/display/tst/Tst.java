package org.zhangbai.display.tst;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tst {

	public static void main(String[] args) {
		String s="[\n  \"{\\n  \\\"Host\\\": \\\"128.224.158.22\\\",\\n  \\\"ID\\\": \\\"TCP:128.224.158.22:44319\\\",\\n  \\\"Port\\\": \\\"44319\\\",\\n  \\\"TransportName\\\": \\\"TCP\\\"\\n}\"\n]";

		System.out.println(s);
		
		String ss = "[-DCPU_VARIANT=_ppc85XX_e6500 -g -DPPC32_fp60x -mhard-float -fno-implicit-fp -D_WRS_HARDWARE_FP -fno-builtin -mstrict-align -ansi -fno-builtin -fno-merge-constants -fno-inline-small-functions -fno-unit-at-a-time -fno-inline-functions-called-once -fno-inline-functions -Wall -mcpu=e500mc -DCPU=PPC32 -DTOOL_FAMILY=gnu -DTOOL=gnu -DPRJ_BUILD -D_WRS_KERNEL -DIP_PORT_VXWORKS=668 -DINET -UCPU -DCPU=PPC85XX -UCPU_VARIANT -DCPU_VARIANT=_e6500, ";
		StringBuffer sb = new StringBuffer(ss);
		
		int i = sb.indexOf("-DCPU=");
		System.out.println(i);
		System.out.println(sb.substring(i));
		
		long currentTime = System.currentTimeMillis();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");

		currentTime = Long.parseLong("1522301262576");
		Date date = new Date(currentTime);
		System.out.println(formatter.format(date));
		
		date = new Date(Long.parseLong("1522301262666"));

		System.out.println(date.getTime());
		
		System.out.println(formatter.format(date));
		
		String line = "-1	392	481	36";
		String[] arr = line.split("\t");
		System.out.println(arr.length);
		
		System.out.println(CHANNEL.CHANNEL_355.name());
		System.out.println(CHANNEL.CHANNEL_355.ordinal());
	}
	
	enum CHANNEL {
		CHANNEL_355,
		CHANNEL_532p,
		CHANNEL_532s,
		CHANNEL_1064,
	}


}
