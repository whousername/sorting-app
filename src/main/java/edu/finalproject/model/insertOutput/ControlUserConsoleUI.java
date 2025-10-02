package edu.finalproject.model.insertOutput;

import edu.finalproject.model.PersonalData;

import java.util.List;
import java.util.Scanner;

public class ControlUserConsoleUI {
    private final InsertOutput insertOutput;
    private final Scanner scanner;

    public ControlUserConsoleUI(InsertOutput insertOutput) {
        this.insertOutput = insertOutput;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Система управления пользователями ===");
            System.out.println("1. Ручной ввод пользователей");
            System.out.println("2. Чтение пользователей из файла");
            System.out.println("3. Генерация случайных пользователей");
            System.out.println("4. Выход");
            System.out.print("Выберите опцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            List<PersonalData> users = List.of();

            switch (choice) {
                case 1:
                    users = insertOutput.manualInput();
                    insertOutput.displayUsers(users);
                    break;

                case 2:
                    System.out.print("Введите имя файла: ");
                    String filename = scanner.nextLine();
                    users = insertOutput.readFromFile(filename);
                    insertOutput.displayUsers(users);
                    break;

                case 3:
                    System.out.println("Введите количество пользователей:");
                    int count = scanner.nextInt();
                    scanner.nextLine();
                    users = insertOutput.generateRandomData(count);
                    insertOutput.displayUsers(users);
                    break;

                case 4:
                    System.out.println("Выход...");
                    return;

                default:
                    System.out.println("Неверный выбор");
                    continue;
            }

            processUsers(users);
        }

    }

    private void processUsers(List<PersonalData> users) {
        System.out.println("\nПолучено пользователей: " + users.size());

        if (users.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }

        // сохранения файла
        System.out.println("\nСоздать файл с результатами? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Сохранить пользователей в отдельный файл в формате .txt" +
                    " или перезаписать существующий (c/r):");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("с")) {
                System.out.println("Назовите файл");
                String fileName = scanner.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users);
            }

            else if (choice.equalsIgnoreCase("r")) {
                System.out.println("Напишите название файла");
                String fileName = scanner.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users, true);
            }else{
                System.out.println("Ошибка ввода");
            }

        }
    }
}
