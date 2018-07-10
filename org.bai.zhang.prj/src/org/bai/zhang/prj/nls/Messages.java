/**
* Messages.java
 * Created on Apr 18, 2011
 *
 * Copyright (c) 2011, 2013 Wind River Systems, Inc.
 *
 * The right to copy, distribute, modify, or otherwise make use
 * of this software may be licensed only pursuant to the terms
 * of an applicable Wind River license agreement.
 */
package org.bai.zhang.prj.nls;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	// The plug-in resource bundle name
	private static final String BUNDLE_NAME = "org.bai.zhang.prj.nls.Messages"; //$NON-NLS-1$

	/**
	 * Static constructor.
	 */
	static {
		// Load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	// **** Declare externalized string id's down here *****
	public static String menu1;
	public static String menu2;
	public static String menu3;
	public static String menu4;
	public static String menu5;
	public static String menu6;
	public static String menu7;
	public static String menu8;

	public static String slash1_menu1;
	public static String slash1_menu2;
	public static String slash1_menu3;
	public static String slash1_menu3_s;
	public static String slash1_menu4;
	public static String slash1_menu5;
	
	public static String slash2_menu1;
	public static String slash2_menu2;
	public static String slash2_menu3;
	public static String slash2_menu4;
	public static String slash2_menu5;
	
	public static String slash3_menu1;
	public static String slash3_menu2;
	
	public static String slash4_menu1;
	
	public static String others_high;
	public static String others_data_ananlyse_done;
	
	public static void main(String[] args) {
		System.out.println(Messages.menu1);
	}

}
