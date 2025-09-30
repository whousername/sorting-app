package edu.finalproject.model;

import java.util.Random;

public class RandomUserGenerator {
    private static long idCounter = 0;

    private static final String[] FIRST_NAMES = {"John", "Alice", "Bob", "Eva", "Mike", "Sara"};
    private static final String[]  LAST_NAMES = {"Doe", "Smith", "Johnson", "Brown", "Wilson", "Davis"};
    private static final Random RANDOM = new Random();

    public static PersonalData generate() {
        String firstName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];

        return new DtoBuilder()
                .id(idCounter++)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
