package com.example.util;

import java.util.Iterator;

public class Util {

	public static String join(Iterable collection, String separator) {
		StringBuilder str = new StringBuilder();
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			str.append(it.next());
			if (it.hasNext()) str.append(separator);
		}
		return str.toString();
	}
	
}

