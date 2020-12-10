package pl.sda.refactoring.util;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sda.refactoring.util.PatternValidator.emailMatches;
import static pl.sda.refactoring.util.PatternValidator.nameMatches;
import static pl.sda.refactoring.util.PatternValidator.peselMatches;

import org.junit.jupiter.api.Test;

final class PatternValidatorTest {

    @Test
    void shouldMatchValidEmail() {
        // given
        final var validEmail = "kamil@test.com";

        // when
        final var matches = emailMatches(validEmail);

        // then
        assertTrue(matches);
    }

    @Test
    void shouldNotMatchOnInvalidEmail() {
        // given
        final var invalidEmail = "test.com";

        // when
        final var matches = emailMatches(invalidEmail);

        // then
        assertFalse(matches);
    }

    @Test
    void shouldMatchValidName() {
        // given
        final var validName = "kamil";

        // when
        final var matches = nameMatches(validName);

        // then
        assertTrue(matches);
    }

    @Test
    void shouldNotMatchInvalidName() {
        // given
        final var invalidName = "kamil3";

        // when
        final var matches = nameMatches(invalidName);

        // then
        assertFalse(matches);
    }

    @Test
    void shouldMatchPesel() {
        // given
        final var validPesel = "90191383844";

        // when
        final var matches = peselMatches(validPesel);

        // then
        assertTrue(matches);
    }

    @Test
    void shouldNotMatchInvalidPesel() {
        // given
        final var invalidPesel = "901913838411";

        // when
        final var matches = peselMatches(invalidPesel);

        // then
        assertFalse(matches);
    }
}