package edu.finalproject.UI;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import edu.finalproject.insertOutput.FileService;
import edu.finalproject.insertOutput.InsertOutput;
import edu.finalproject.insertOutput.Status;
import edu.finalproject.insertOutput.WarningColors;
import edu.finalproject.model.PersonalData;
import edu.finalproject.search.SearchEngine;

public class UIService {

    private final Scanner scan = new Scanner(System.in);

    private final InsertOutput insertOutput = new InsertOutput();

    FileService fileService = new FileService();
    
    private List<PersonalData> users = new ArrayList<>(); 


    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Выберите действие: ");
            running = handleMainChoice(choice);
            pressEnterToContinue();
            
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
            case 2 -> { handleBinarySearchService();; yield true; }
            case 3 -> { handleFileServiceMenu(); yield true; }
            case 4 -> 
            { 
                fileService.processUsers(users);
                System.out.println("\nДо свидания!"); yield false;
            }
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
            case 1 -> {
                users = insertOutput.manualInput();       
                insertOutput.displayUsers(users);   
            }
            case 2 ->{
                users = insertOutput.generateRandomData();
                WarningColors.printStatus(Status.SUCCESS, "Список пользователей успешно создан!");
                insertOutput.displayUsers(users);
            }
            case 3 -> {
                System.out.println("Укажите название файла: ");
                String filename = readString();
                users = insertOutput.readFromSavedFile(filename + "txt"); //пришлось исправить ибо тут было не првильное название метода
                insertOutput.displayUsers(users);
            }
            case 4 -> {
                System.out.println("Возврат в главное меню...");  
            }
            default -> {
                System.out.println("Неверный выбор!"); 
            }
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

    private void handleBinarySearchService(){
        System.out.println("ОПЦИИ ПОИСКА: ");
        System.out.println("1. Поиск по ID");
        System.out.println("2. Поиск по имени");
        System.out.println("3. Поиск по фамилии");
        System.out.println("4. Назад");

        int choice = readInt("Выберете параметр поиска: ");
        switch (choice) {
            case 1 -> binarySearchGeneric(PersonalData::getId, "Введите ID: ", Long::valueOf);
            case 2 -> binarySearchGeneric(PersonalData::getFirstName, "Введите имя: ", String::trim);
            case 3 -> binarySearchGeneric(PersonalData::getLastName, "Введите фамилию: ", String::trim);
            case 4 -> System.out.println("Возврат в главное меню");
            default -> System.out.println("Неверный выбор");
        }
    }

    private <F extends Comparable<? super F>> void binarySearchGeneric(
        Function<PersonalData, F> getter,
        String promtMessage,
        Function<String, F> parser)
    {
        users.sort(Comparator.comparing(getter));
        SearchEngine<PersonalData, F> binarySearch = new SearchEngine<>(users, getter, Comparator.naturalOrder());
        System.out.println(promtMessage);
        String inputRaw = readString();

        try {
            F key = parser.apply(inputRaw.trim());
            PersonalData found = binarySearch.find(key);
            if(found != null){
                System.out.println("Пользователь найден: " + found);
            } else {System.out.println("Пользователь не найден");}
        } catch (RuntimeException e) {
            System.out.println("Неверный формат ввода: " + e.getMessage());
        }
    }

    private String readString(){
        while(!scan.hasNextLine()){
            System.out.println("Введите строку корректно");
            scan.nextLine();
        } return scan.nextLine();
    }

    private int readInt(String prompt) {
        System.out.println(prompt);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("Введите корректное число!");
        }
        int i = scan.nextInt();
        scan.nextLine(); 
        return i;
    }

    private Long readLongId() {
        while (!scan.hasNextLong()) {
            System.out.println("Введите корректный ID (число):");
            scan.next();
        }
        return scan.nextLong();
    }

    public void pressEnterToContinue() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        if (scan.hasNextLine()) {
            scan.nextLine(); 
        }
        scan.nextLine(); 
    }
}