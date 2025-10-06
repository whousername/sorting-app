package edu.finalproject.sortAlgorithms;

import edu.finalproject.model.PersonalData;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SortAlgorithmsTest {

    @Test
    void testBubbleSort() {
        CustomUserCollection<PersonalData> users = createTestCollection();
        SortStrategy<PersonalData> strategy = new BubbleSort<>();
        Comparator<PersonalData> comparator = Comparator.comparing(PersonalData::getId);

        strategy.sort(users, comparator);

        // Проверяем что коллекция отсортирована по ID
        for (int i = 0; i < users.size() - 1; i++) {
            assertTrue(users.get(i).getId() <= users.get(i + 1).getId());
        }
    }

    @Test
    void testSelectionSort() {
        CustomUserCollection<PersonalData> users = createTestCollection();
        SortStrategy<PersonalData> strategy = new SelectionSort<>();
        Comparator<PersonalData> comparator = Comparator.comparing(PersonalData::getFirstName);

        strategy.sort(users, comparator);

        // Проверяем что коллекция отсортирована по имени
        for (int i = 0; i < users.size() - 1; i++) {
            assertTrue(users.get(i).getFirstName().compareTo(users.get(i + 1).getFirstName()) <= 0);
        }
    }

    private CustomUserCollection<PersonalData> createTestCollection() {
        CustomUserCollection<PersonalData> users = new CustomUserCollection<>();
        users.add(createUser(3L, "Charlie", "Brown"));
        users.add(createUser(1L, "Alice", "Smith"));
        users.add(createUser(2L, "Bob", "Adams"));
        users.add(createUser(4L, "Diana", "Clark"));
        return users;
    }

    private PersonalData createUser(Long id, String firstName, String lastName) {
        return new edu.finalproject.model.DtoBuilder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}