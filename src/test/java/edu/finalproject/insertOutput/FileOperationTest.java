package edu.finalproject.insertOutput;

import edu.finalproject.model.PersonalData;
import edu.finalproject.sortAlgorithms.CustomUserCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationsTest {

    private CustomUserCollection<PersonalData> testUsers;
    private final String TEST_FILENAME = "test_users.txt";

    @BeforeEach
    void setUp() {
        testUsers = new CustomUserCollection<>();
        testUsers.add(createUser(1L, "John", "Doe"));
        testUsers.add(createUser(2L, "Alice", "Smith"));
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(Path.of(TEST_FILENAME))) {
            Files.delete(Path.of(TEST_FILENAME));
        }
    }

    private PersonalData createUser(Long id, String firstName, String lastName) {
        return new edu.finalproject.model.DtoBuilder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    @Test
    void testFileSaveAndRead() {
        InsertOutput insertOutput = new InsertOutput();

        // Сохраняем в файл
        insertOutput.saveToFile(TEST_FILENAME, testUsers, false);

        // Читаем из файла
        CustomUserCollection<PersonalData> loadedUsers = insertOutput.readFromSavedFile(TEST_FILENAME);

        assertNotNull(loadedUsers);
        assertEquals(testUsers.size(), loadedUsers.size());
    }

    @Test
    void testFileUserParser() {
        String validLine = "USER: John Doe (ID: 1)";
        PersonalData user = FileUserParser.parseLine(validLine, 1L);

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(1L, user.getId());
    }
}