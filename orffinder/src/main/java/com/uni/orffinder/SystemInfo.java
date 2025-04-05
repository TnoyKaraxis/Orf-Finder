package com.uni.orffinder;

/**
 * a class for the SystemInfo
 */
public class SystemInfo {

    /**
     * @return the java version
     */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * @return the javafx version
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}