package org.diegosneves.domain.utils;

public class Cleaner {

    private static final String BLANK_STRING = "";

    private Cleaner() {}

    public static String string(String aString) {
        return aString == null ? BLANK_STRING : aString.trim();
    }

}
