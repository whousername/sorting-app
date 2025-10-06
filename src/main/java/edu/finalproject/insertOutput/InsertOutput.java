package edu.finalproject.insertOutput;

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;
import edu.finalproject.sortAlgorithms.CustomUserCollection;

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
   
    public CustomUserCollection<PersonalData> manualInput() {
        CustomUserCollection<PersonalData> users = new CustomUserCollection<>();

        
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
   
     public CustomUserCollection<PersonalData> generateRandomData() {
        CustomUserCollection<PersonalData> users = new CustomUserCollection<>();
        int count = 0;
        System.out.println("Введите количество случайных пользователей:");
        try {
            count = ManualUserInput.readPositiveInt(scanner);
        } catch (Exception e) {
            logger.warning("Ошибка при чтении количества: " + e.getMessage());
            return users;
        }

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
    public void displayUsers(CustomUserCollection<PersonalData> users) {
        if (users == null || users.getSize() == 0) {
            System.out.println("Список пользователей пуст.");
            return;
        }

        System.out.println("\n=== Список пользователей ===");
        System.out.println("Всего пользователей: " + users.getSize());
        System.out.println("----------------------------------------");
        users.stream().peek(user -> {
            System.out.printf("USER: %s%n", user.toString());
            System.out.println("----------------------------------------");
        }).toList();
    }

    public void saveToFile(String filename, CustomUserCollection<PersonalData> users, boolean append) {
        if (users == null || users.getSize() == 0) {
            logger.warning("Попытка сохранить пустой список пользователей.");
            return;
        }


        try {
            CustomUserCollection<PersonalData> usersToSave = append ? prepareUsersForAppend(filename, users) : users;
            writeToFile(filename, usersToSave, append);
            WarningColors.printStatus(Status.SUCCESS, append ? "Файл успешно перезаписан" : "Файл успешно создан");

            System.out.printf("Пользователи успешно сохранены в файл: %s (режим: %s)%n",
                    filename, append ? "добавление" : "перезапись");
        } catch (IOException e) {
            logger.severe("Ошибка работы с файлом: " + e.getMessage());
        }
    }

    public void saveToFile(String filename, CustomUserCollection<PersonalData> users) {
        saveToFile(filename, users, false);
    }

    //парсер для добавления новых юзеров с правильными id
    private CustomUserCollection<PersonalData> prepareUsersForAppend(String filename, CustomUserCollection<PersonalData> users) throws IOException {
        if (Files.notExists(Paths.get(filename))) {
            return users;
        }

        long startId = FileUserReader.getMaxIdFromLines(Files.readAllLines(Paths.get(filename))) + 1;
        CustomUserCollection<PersonalData> result = new CustomUserCollection<>();

        for (int i = 0; i < users.getSize(); i++) {
            PersonalData user = users.get(i);
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

    private void writeToFile(String filename, CustomUserCollection<PersonalData> users, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            boolean fileExists = Files.exists(Paths.get(filename)) && Files.size(Paths.get(filename)) > 0;

            if (!append || !fileExists) {
                writer.write(FileUserReader.getHeader(users.getSize()));
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
    public CustomUserCollection<PersonalData> readFromSavedFile(String filename) {
        CustomUserCollection<PersonalData> users = new CustomUserCollection<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            CustomUserCollection<PersonalData> lines = new CustomUserCollection<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            users = FileUserReader.parseSavedFile(lines);

            WarningColors.printStatus(Status.SUCCESS, "Файл успешно прочитан");

            System.out.println("Прочитано пользователей из файла: " + users.getSize());

        } catch (IOException e) {
            logger.severe("Ошибка чтения файла: " + e.getMessage());
        }
       return users;
    }

    /**
     * READER с запросом названия файла
     */
    public CustomUserCollection<PersonalData> readFromSavedFile() {
        System.out.print("Введите имя файла: ");
        String filename = scanner.nextLine();
        return readFromSavedFile(filename);
    }
}
