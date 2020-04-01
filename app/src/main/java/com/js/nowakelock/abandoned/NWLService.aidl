// NWLService.aidl
package com.js.nowakelock;

// Declare any non-default types here with import statements

interface NWLService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    void upCount(String packageName,String wakelockName);
    void upBlockCount(String packageName,String wakelockName);
    boolean getFlag(String packageName,String wakelockName);
}
