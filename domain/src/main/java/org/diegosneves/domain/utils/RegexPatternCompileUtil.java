package org.diegosneves.domain.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
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
            log.error("Regex Pattern invalid -> {}", regex);
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
