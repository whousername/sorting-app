package edu.finalproject.insertOutput;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    void testUserValidator_ValidNames() {
        assertDoesNotThrow(() -> {
            UserValidator.validateUsername("John");
            UserValidator.validateUsername("Анна");
            UserValidator.validateUsername("Мария");
            UserValidator.validateUsername("Alex");
        });
    }

    @Test
    void testUserValidator_InvalidNames() {
        assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUsername("Al"));

        assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUsername("VeryLongNameThatExceedsLimit"));

        assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUsername("John123"));

        assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUsername("John@Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                UserValidator.validateUsername(null));
    }
}