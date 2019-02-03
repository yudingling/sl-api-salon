package com.sl.api.salon.util;

public class FloatUtil {
	public static Double toFix(Double value, int precision){
		return Double.parseDouble(String.format("%."+ precision +"f", value));
	}
}