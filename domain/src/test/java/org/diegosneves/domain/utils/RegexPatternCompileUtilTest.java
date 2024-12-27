package org.diegosneves.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegexPatternCompileUtilTest {

    @Test
    void shouldReturnTrueGivenStringWithoutDigits() {
        final String regexPattern = "^[\\D]+$";
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        Assertions.assertTrue(actual);
    }

    @Test
    void shouldReturnFalseGivenStringWithDigits() {
        final String regexPattern = "^[\\D]+$";
        final String input = "54898";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        Assertions.assertFalse(actual);
    }

    @Test
    void shouldReturnFalseGivenInvalidRegexPattern() {
        final String regexPattern = "Teste";
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldReturnFalseGivenBlanckInput(String input) {
        final String regexPattern = "^[\\D]+$";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldReturnFalseGivenBlankRegex(String regexPattern) {
        final String input = "dferste";

        final var actual = RegexPatternCompileUtil.matches(regexPattern, input);

        Assertions.assertFalse(actual);
    }

}
