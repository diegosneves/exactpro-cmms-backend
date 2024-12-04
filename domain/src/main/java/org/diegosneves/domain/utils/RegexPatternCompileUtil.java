package org.diegosneves.domain.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternCompileUtil {

    private RegexPatternCompileUtil() {
    }

    public static boolean matches(String regex, String input) {
        Pattern pattern = null;
        if (!isValidInput(input) || !isValidRegex(regex)) {
            return false;
        }
        try {
            pattern = Pattern.compile(regex);
        } catch (Exception ignore) {
            return false;
        }
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private static boolean isValidRegex(String regex) {
        return regex != null && !regex.isEmpty() && !regex.isBlank();
    }

    private static boolean isValidInput(String input) {
        return input != null && !input.isEmpty() && !input.isBlank();
    }

}
