package edu.finalproject.search;

import edu.finalproject.model.PersonalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SearchEngineTest {

    private List<PersonalData> testUsers;

    @BeforeEach
    void setUp() {
        testUsers = new ArrayList<>();
        testUsers.add(createUser(1L, "John", "Doe"));
        testUsers.add(createUser(2L, "Alice", "Smith"));
        testUsers.add(createUser(3L, "Bob", "Johnson"));
    }

    private PersonalData createUser(Long id, String firstName, String lastName) {
        return new edu.finalproject.model.DtoBuilder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    @Test
    void testBinarySearch_FindById() {
        List<PersonalData> sortedUsers = testUsers.stream()
                .sorted(Comparator.comparing(PersonalData::getId))
                .collect(Collectors.toList());

        SearchEngine<PersonalData, Long> searchEngine = new SearchEngine<>(
                sortedUsers,
                PersonalData::getId,
                Comparator.naturalOrder()
        );

        PersonalData found = searchEngine.find(2L);
        assertNotNull(found);
        assertEquals("Alice", found.getFirstName());
        assertEquals("Smith", found.getLastName());
    }

    @Test
    void testBinarySearch_FindByFirstName() {
        List<PersonalData> sortedUsers = testUsers.stream()
                .sorted(Comparator.comparing(PersonalData::getFirstName))
                .collect(Collectors.toList());

        SearchEngine<PersonalData, String> searchEngine = new SearchEngine<>(
                sortedUsers,
                PersonalData::getFirstName,
                Comparator.naturalOrder()
        );

        PersonalData found = searchEngine.find("Bob");
        assertNotNull(found);
        assertEquals("Bob", found.getFirstName());
        assertEquals(3L, found.getId());
    }

    @Test
    void testBinarySearch_NotFound() {
        List<PersonalData> sortedUsers = testUsers.stream()
                .sorted(Comparator.comparing(PersonalData::getId))
                .collect(Collectors.toList());

        SearchEngine<PersonalData, Long> searchEngine = new SearchEngine<>(
                sortedUsers,
                PersonalData::getId,
                Comparator.naturalOrder()
        );

        PersonalData found = searchEngine.find(999L);
        assertNull(found);
    }
}