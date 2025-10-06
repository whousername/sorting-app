package edu.finalproject.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @Test
    void testDtoBuilder() {
        PersonalData user = new DtoBuilder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void testDtoBuilder_Validation() {
        assertThrows(IllegalStateException.class, () -> {
            new DtoBuilder()
                    .id(null)
                    .firstName("John")
                    .lastName("Doe")
                    .build();
        });

        assertThrows(IllegalStateException.class, () -> {
            new DtoBuilder()
                    .id(1L)
                    .firstName(null)
                    .lastName("Doe")
                    .build();
        });
    }
}