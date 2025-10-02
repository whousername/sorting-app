package edu.finalproject.UI;

import java.util.Scanner;

public class UIService {

    private final Scanner scan = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Выберите действие: ");
            running = handleMainChoice(choice);
        }
    }

    private void printMainMenu() {
        System.out.println("= = =  П Р И Л О Ж Е Н И Е  Д Л Я  С О Р Т И Р О В К И  = = =");
        System.out.println("МЕНЮ:");
        System.out.println("1. Выбрать способ заполнения");
        System.out.println("2. Бинарный поиск");
        System.out.println("3. Работа с файлами");
        System.out.println("4. Выход");
    }

    private boolean handleMainChoice(int choice) {
        return switch (choice) {
            case 1 -> { handleFillingMenu(); yield true; }
            case 2 -> { binarySearch(); yield true; }
            case 3 -> { handleFileServiceMenu(); yield true; }
            case 4 -> { System.out.println("До свидания!"); yield false; }
            default -> {
                System.out.println("Неверный выбор. Попробуйте снова.");
                yield true;
            }
        };
    }

    private void handleFillingMenu() {
        System.out.println("СПОСОБЫ ЗАПОЛНЕНИЯ:");
        System.out.println("1. Ручное заполнение");
        System.out.println("2. Заполнить случайными данными");
        System.out.println("3. Заполнить из файла");
        System.out.println("4. Назад");

        int choice = readInt("Выберите опцию: ");
        switch (choice) {
            case 1, 2, 3 -> {
                int len = chooseArrayLength();
                System.out.println("Выбранное количество пользователей: " + len);
                pressEnterToContinue();
                
            }
            case 4 -> System.out.println("Возврат в главное меню...");
            default -> System.out.println("Неверный выбор!");
        }
    }

    private void handleFileServiceMenu() {
        System.out.println("ОПЦИИ РАБОТЫ С ФАЙЛАМИ:");
        System.out.println("1. Найти пользователей и записать в файл");
        System.out.println("2. Записать отсортированную коллекцию в файл");
        System.out.println("3. Назад");

        int choice = readInt("Выберите опцию: ");
        switch (choice) {
            case 1 -> System.out.println("TODO: реализовать поиск и запись");
            case 2 -> System.out.println("TODO: реализовать запись отсортированной коллекции");
            case 3 -> System.out.println("Возврат в главное меню...");
            default -> System.out.println("Неверный выбор!");
        }
    }

    private void binarySearch() {
        System.out.println("TODO: реализовать бинарный поиск");
        pressEnterToContinue();
    }

    private int chooseArrayLength() {
        return readInt("Укажите количество пользователей...");
    }

    private int readInt(String prompt) {
        System.out.println(prompt);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("Введите корректное число!");
        }
        int i = scan.nextInt();
        scan.nextLine(); // потребление \n
        return i;
    }

    public String pressEnterToContinue() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        return scan.nextLine();
    }
}