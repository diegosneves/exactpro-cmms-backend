package org.diegosneves.exactprocmmsbackend.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RegexPatternCompileUtilTest {

    @Test
    void shouldReturnTrueGivenStringWithoutDigits() {
        final String regexPattern = "^[\\D]+$";
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        assertTrue(actual);
    }

    @Test
    void shouldReturnFalseGivenStringWithDigits() {
        final String regexPattern = "^[\\D]+$";
        final String input = "54898";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        assertFalse(actual);
    }

    @Test
    void shouldReturnFalseGivenInvalidRegexPattern() {
        final String regexPattern = "Teste";
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldReturnFalseGivenBlanckInput(String input) {
        final String regexPattern = "^[\\D]+$";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldReturnFalseGivenBlankRegex(String regexPattern) {
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        assertFalse(actual);
    }

}