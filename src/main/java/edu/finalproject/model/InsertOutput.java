package edu.finalproject.model;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class InsertOutput {
    private static final Logger logger = Logger.getLogger(InsertOutput.class.getName());
    private static long idCounter = 0;

    // Ввод юзеров через консоль
    public List<PersonalData> manualInput() {
        List<PersonalData> users = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество пользователей:");
        int count = ManualUserInput.readPositiveInt(scanner);

        for (int i = 0; i < count; i++) {
            boolean valid = false;
            while (!valid) {
                try {
                    users.add(ManualUserInput.readUserFromConsole(scanner));
                    valid = true;
                } catch (Exception e) {
                    logger.warning("Ошибка ввода: " + e.getMessage());
                }
            }
        }
        return users;
    }

    //Генерация случайных данных
    public List<PersonalData> generateRandomData(int count) {
        List<PersonalData> users = new ArrayList<>();

        if (count < 0){
            logger.severe("Количество генерируемых юзеров не может быть меньше нуля!");
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

    //чтение из файла
    public List<PersonalData> readFromFile(String filename) {
        List<PersonalData> users = new ArrayList<>();
        String line = "";
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    PersonalData user = FileUserReader.parseLine(line, idCounter++);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (Exception e) {
                    logger.severe("Невалидная строка " + lineNumber + ": " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Ошибка чтения: " + e.getMessage() + "\n");
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
        users.stream().peek(user -> {
            System.out.printf("USER: %s%n", user.toString());
            System.out.println("----------------------------------------");
        }).toList();
    }

    public void saveToFile(String filename, List<PersonalData> users) {
        if (users == null || users.isEmpty()) {
            logger.warning("Попытка сохранить пустой список пользователей.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("=== Список пользователей ===\n");
            writer.write("Всего пользователей: " + users.size() + "\n");
            writer.write("----------------------------------------\n");

            String content = users.stream()
                    .map(user -> "USER: " + user
                            + "\n----------------------------------------\n")
                    .collect(Collectors.joining());

            writer.write(content);

            System.out.printf("Пользователи успешно сохранены в файл: %s", filename);
        } catch (IOException e) {
            logger.severe("Ошибка записи в файл: " + e.getMessage());
        }
    }

}
