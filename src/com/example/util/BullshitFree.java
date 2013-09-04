package com.example.util;



@SuppressWarnings("serial")
public class BullshitFree extends RuntimeException {

	public BullshitFree(Throwable cause) {
		super(cause);
	}
	
	
	public static <V> BullshitFreeMethod<V> method(Class jazz, String name, Class... types) {
		return new BullshitFreeMethod<V>(jazz, name, types);
	}
	
}
