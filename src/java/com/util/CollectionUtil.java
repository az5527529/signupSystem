package com.util;

import java.util.Collection;

public class CollectionUtil {
	public static boolean isEmptyCollection(Collection collection){
		if(collection==null||collection.isEmpty()){
			return true;
		}
		return false;
	}
}
