package com.example.util;

import java.lang.reflect.Method;

public class BullshitFreeMethod<V> {

	private Method method;
	
	
	public BullshitFreeMethod(Class jazz, String name, Class... types) {
		try {
			method = jazz.getDeclaredMethod(name,types);
			method.setAccessible(true);
		} catch (Exception exception) {
			throw new BullshitFree(exception);
		}
	}
	
	
	public V invoke(Object obj, Object... args) {
		try {
			return (V) method.invoke(obj, args);
		} catch (Exception exception) {
			throw new BullshitFree(exception);
		}
	}
	
}
