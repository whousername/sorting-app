<<<<<<<< HEAD:src/main/java/edu/finalproject/inputOutput/RandomUserGenerator.java
package edu.finalproject.inputOutput;
========
package edu.finalproject.insertOutput;
>>>>>>>> integration:src/main/java/edu/finalproject/insertOutput/RandomUserGenerator.java

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;

import java.util.Random;

public class RandomUserGenerator {
    private static long idCounter = 0;

    private static final String[] FIRST_NAMES = {"Джон", "Алиса", "Боб", "Ева", "Майк", "Сара"};
    private static final String[] LAST_NAMES = {"Дое", "Смит", "Джонсон", "Браун", "Уилсон", "Дэвис"};
    private static final Random RANDOM = new Random();

    public static PersonalData generate() {
        String firstName = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];

        /*todo: Возможно нужно заменить Random на ThreadLocalRandom по причине многопоточности
         * String firstName = FIRST_NAMES[ThreadLocalRandom.current().nextInt(FIRST_NAMES.length)];*/

        return new DtoBuilder()
                .id(idCounter++)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
