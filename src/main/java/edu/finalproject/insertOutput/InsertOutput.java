<<<<<<<< HEAD:src/main/java/edu/finalproject/inputOutput/InsertOutput.java
package edu.finalproject.inputOutput;
========
package edu.finalproject.insertOutput;
>>>>>>>> integration:src/main/java/edu/finalproject/insertOutput/InsertOutput.java

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InsertOutput {
    private static final Logger logger = Logger.getLogger(InsertOutput.class.getName());
    private Scanner scanner = new Scanner(System.in);

    // Ввод юзеров через консоль
    public List<PersonalData> manualInput() {
        List<PersonalData> users = new ArrayList<>();

        System.out.println("Введите количество пользователей:");
        int count = ManualUserInput.readPositiveInt(scanner);

        for (int i = 0; i < count; i++) {
            boolean valid = false;
            while (!valid) {
                try {
                    System.out.println("---------новый пользователь: " + i);
                    users.add(ManualUserInput.readUserFromConsole(scanner));
                    valid = true;
                } catch (Exception e) {
                    logger.warning("Ошибка ввода: " + e.getMessage());
                }
            }
        }
        WarningColors.printStatus(Status.SUCCESS, "Ввод пользователей успешно завершен");
        return users;
    }

    //Генерация случайных данных
    public List<PersonalData> generateRandomData() {
        List<PersonalData> users = new ArrayList<>();

<<<<<<<< HEAD:src/main/java/edu/finalproject/inputOutput/InsertOutput.java
        if (count < 0) {
            logger.severe("Количество генерируемых юзеров не может быть меньше нуля!");
            return users;
        }
========
        System.out.println("Введите количество пользователей:");
        int count = ManualUserInput.readPositiveInt(scanner);
>>>>>>>> integration:src/main/java/edu/finalproject/insertOutput/InsertOutput.java

        for (int i = 0; i < count; i++) {
            try {
                PersonalData user = RandomUserGenerator.generate();
                users.add(user);
            } catch (Exception e) {
                logger.warning("Ошибка при генерации: " + e.getMessage());
            }
        }

        return users;
    }

    //вывод пользователей
    public void displayUsers(List<PersonalData> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
            return;
        }

        System.out.println("\n=== Список пользователей ===");
        System.out.println("Всего пользователей: " + users.size());
        System.out.println("----------------------------------------");

        /*todo: .peek() зачастую ведет себя "неправильно", возможно стоит заменить на
         * users.stream().forEach(user -> {
         * System.out.printf("USER: %s%n", user.toString());
         * System.out.println("----------------------------------------");*/

        users.stream().peek(user -> {
            System.out.printf("USER: %s%n", user.toString());
            System.out.println("----------------------------------------");
        }).toList();
    }

    public void saveToFile(String filename, List<PersonalData> users, boolean append) {
        if (users == null || users.isEmpty()) {
            logger.warning("Попытка сохранить пустой список пользователей.");
            return;
        }

        try {
            List<PersonalData> usersToSave = append ? prepareUsersForAppend(filename, users) : users;
            writeToFile(filename, usersToSave, append);
            WarningColors.printStatus(Status.SUCCESS, append ? "Файл успешно перезаписан" : "Файл успешно создан");

            System.out.printf("Пользователи успешно сохранены в файл: %s (режим: %s)%n",
                    filename, append ? "добавление" : "перезапись");
        } catch (IOException e) {
            logger.severe("Ошибка работы с файлом: " + e.getMessage());
        }
    }

    public void saveToFile(String filename, List<PersonalData> users) {
        saveToFile(filename, users, false);
    }

    //парсер для добавления новых юзеров с правильными id
    private List<PersonalData> prepareUsersForAppend(String filename, List<PersonalData> users) throws IOException {
        if (Files.notExists(Paths.get(filename))) {
            return users;
        }

        long startId = FileUserReader.getMaxIdFromLines(Files.readAllLines(Paths.get(filename))) + 1;
        List<PersonalData> result = new ArrayList<>();

        for (PersonalData user : users) {
            Pattern pattern = Pattern.compile("(.+?)\\s+(.+?)\\s+\\(ID:\\s*\\d+\\)");
            Matcher matcher = pattern.matcher(user.toString());

            if (matcher.find()) {
                PersonalData newUser = new DtoBuilder()
                        .id(startId++)
                        .firstName(matcher.group(1))
                        .lastName(matcher.group(2))
                        .build();
                result.add(newUser);
            } else {
                System.err.println("Не удалось распарсить пользователя: " + user);
            }
        }
        return result;
    }

    private void writeToFile(String filename, List<PersonalData> users, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            boolean fileExists = Files.exists(Paths.get(filename)) && Files.size(Paths.get(filename)) > 0;

            if (!append || !fileExists) {
                writer.write(FileUserReader.getHeader(users.size()));
            } else {
                writer.write("\n=== Дополнительные пользователи ===\n");
            }

            String content = users.stream()
                    .map(user -> "USER: " + user
                            + "\n----------------------------------------\n")
                    .collect(Collectors.joining());
            writer.write(content);
        }
    }

    /**
     * READER файла с парсером
     */
    public List<PersonalData> readFromSavedFile(String filename) {
        List<PersonalData> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            users = FileUserReader.parseSavedFile(lines);

            WarningColors.printStatus(Status.SUCCESS, "Файл успешно прочитан");

            System.out.println("Прочитано пользователей из файла: " + users.size());

        } catch (IOException e) {
            logger.severe("Ошибка чтения файла: " + e.getMessage());
        }

        return users;
    }

    /**
     * READER с запросом названия файла
     */
    public List<PersonalData> readFromSavedFile() {
        System.out.print("Введите имя файла: ");
        String filename = scanner.nextLine();
        return readFromSavedFile(filename);
    }
}
