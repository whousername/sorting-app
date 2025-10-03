package edu.finalproject.insertOutput;

import edu.finalproject.model.PersonalData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class InsertOutput {
    private static final Logger logger = Logger.getLogger(InsertOutput.class.getName());
    //private static long idCounter = 0;
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
    public List<PersonalData> generateRandomData() {
        List<PersonalData> users = new ArrayList<>();

        System.out.println("Введите количество пользователей:");  //я добавил здесь запрос на кол. пользователей. В manualInput изначально был запрос на количество пользователей прямо в методе,
        int count = ManualUserInput.readPositiveInt(scanner);      //а этот метод передавал эту работу в UI 

        // if (count < 0){   //закомментил, эта проверка никогда не сработает тк у нас есть readPositiveInt() и используется строкой выше
        //     logger.severe("Количество генерируемых юзеров не может быть меньше нуля!");
        //     return users;
        // }

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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append))) {
            if (!append || Files.notExists(Paths.get(filename)) || Files.size(Paths.get(filename)) == 0) {
                writer.write("=== Список пользователей ===\n");
                writer.write("Всего пользователей: " + users.size() + "\n");
                writer.write("----------------------------------------\n");
            } else {
                // В режиме добавления - разделитель между сессиями
                writer.write("\n=== Дополнительные пользователи ===\n");
            }

            String content = users.stream()
                    .map(user -> "USER: " + user
                            + "\n----------------------------------------\n")
                    .collect(Collectors.joining());

            writer.write(content);

            System.out.printf("Пользователи успешно сохранены в файл: %s (режим: %s)",
                    filename, append ? "добавление" : "перезапись");
        } catch (IOException e) {
            logger.severe("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public void saveToFile(String filename, List<PersonalData> users) {
        saveToFile(filename, users, false);
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



    //READER файлов со старым парсером 
    // public List<PersonalData> readFromFile() {
    //     List<PersonalData> users = new ArrayList<>();
    //     String line = "";
    //     int lineNumber = 0;

    //     System.out.print("Введите имя файла: ");
    //     String filename = scanner.nextLine();

    //     try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    //         while ((line = reader.readLine()) != null) {
    //             lineNumber++;
    //             try {
    //                 PersonalData user = FileUserReader.parseLine(line, idCounter++);
    //                 if (user != null) {
    //                     users.add(user);
    //                 }
    //             } catch (Exception e) {
    //                 logger.severe("Невалидная строка " + lineNumber + ": " + line + " - " + e.getMessage());
    //             }
    //         }
    //     } catch (IOException e) {
    //         logger.severe("Ошибка чтения: " + e.getMessage() + "\n");
    //     }

    //     return users;
    // }


}
