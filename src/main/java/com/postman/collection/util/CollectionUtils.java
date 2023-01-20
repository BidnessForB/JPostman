package com.postman.collection.util;

import java.lang.reflect.Array;



public class CollectionUtils {
    
    @SuppressWarnings("unchecked")
    public static <T> T[] arrayConcat(T[]... arrays) {
        int totalLen = 0;
        for (T[] arr: arrays) {
            if(arr != null) {
                totalLen += arr.length;
            }
            
        }
        T[] all = (T[])Array.newInstance(
            arrays.getClass().getComponentType().getComponentType(), totalLen);
        int copied = 0;
        for (T[] arr: arrays) {
            if(arr != null) {
                System.arraycopy(arr, 0, all, copied, arr.length);
                copied += arr.length;
            }
            
        }
        return all.length == 0 ? null : all;
    }

    @SuppressWarnings("unchecked")
public static <T> T[] insertInCopy(T[] src, T obj, int i) throws Exception {
    if(i < 0) {
        i = 0;
    }
    else if(i > src.length) {
        i = src.length - 1;
    }
    T[] dst = (T[]) Array.newInstance(src.getClass().getComponentType(), src.length + 1);
    System.arraycopy(src, 0, dst, 0, i);
    dst[i] = obj;
    System.arraycopy(src, i, dst, i + 1, src.length - i);
    return dst;
}

public static <T> T[] insertInCopy(T[] src, T obj) throws Exception {
    return CollectionUtils.insertInCopy(src, obj, src.length);
}
}
