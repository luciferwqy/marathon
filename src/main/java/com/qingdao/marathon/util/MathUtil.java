package com.qingdao.marathon.util;

import java.text.DecimalFormat;

public class MathUtil {

	/**
	 * 乘法
	 */
	public static String mul(String a,String b)throws Exception{
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format((Double.valueOf(a)*Double.valueOf(b)));
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(mul("20.11111", "6.139"));
	}
}


