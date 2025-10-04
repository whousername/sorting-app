package edu.finalproject.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.finalproject.insertOutput.FileServiceTask2;
import edu.finalproject.insertOutput.InsertOutput;
import edu.finalproject.insertOutput.Status;
import edu.finalproject.insertOutput.WarningColors;
import edu.finalproject.model.PersonalData;

public class UIService {

    private final Scanner scan = new Scanner(System.in);

    private final InsertOutput insertOutput = new InsertOutput();

    FileServiceTask2 fileService = new FileServiceTask2();
    
    private List<PersonalData> users = new ArrayList<>(); 
    

    //проблема: мы распарсили файл 5 имен, выходим из программы и сохраняем в тот же файл: получаем 10 имен - каждого по два

    //проблема повторяющихся id: мы создали два пользователя id0 и id1, добавляем эти два пользователя в файл в котором уже существуют такие id


    /*Введите количество пользователей:
                    2
                    Введите имя: sdf      
                    Введите фамилию: cdws      //нет разделений между созданием юзеров, то есть просто подряд: введите имя, введите фамилию, введите имя и тд
                    Введите имя: asdf          //между созданием пользователей какой то разделитель нужен 
                    Введите фамилию: sdf */
    //

    //свободно для доработки в последнюю очередь: цветнной текст ошибок в меню


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
            case 2 -> { binarySearch(); yield true; }
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
                users = insertOutput.readFromSavedFile();
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

    private void binarySearch() {
        System.out.println("TODO: реализовать бинарный поиск");
        pressEnterToContinue();
    }

    // private int chooseArrayLength() {
    //     return readInt("Укажите количество пользователей...");
    // }

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

    public String pressEnterToContinue() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        return scan.nextLine();
    }


    /*
    
    
     на данном этапе наша утилита сохраняет файлы а одном формате, а десериализует из другого формата.
     проще говоря, мы не можем прочитать файл который создали нашей же программой
     1.переписать метод который будет сохранять в файл в формате Имя,Фамилия МИНУСЫ: красивый метод антона going to hell
     2.сгенерировать сложный парсер под формат в котором сохраняет метод Антона. МИНУСЫ: это будет копипаста от курсора, я не буду сидеть разбираться как написать огромный парсер





    */



    //перенес в файлсервис 


    // private void processUsers(List<PersonalData> users) {
    //     System.out.println("\nПолучено пользователей: " + users.size());

    //     if (users.isEmpty()) {
    //         System.out.println("Список пуст");
    //         return;
    //     }
    //     System.out.println("\nСоздать файл с результатами? (y/n): ");
    //     if (scan.nextLine().equalsIgnoreCase("y")) {
    //         System.out.print("Сохранить пользователей в отдельный файл в формате .txt" +
    //                 " или перезаписать существующий (c/r):");

    //         String choice = scan.nextLine();

    //         if (choice.equalsIgnoreCase("c")) {
    //             System.out.println("Назовите файл");
    //             String fileName = scan.nextLine();
    //             insertOutput.saveToFile(fileName + ".txt", users);
    //         }

    //         else if (choice.equalsIgnoreCase("r")) {
    //             System.out.println("Напишите название файла");
    //             String fileName = scan.nextLine();
    //             insertOutput.saveToFile(fileName + ".txt", users, true);
    //         }else{
    //             System.out.println("Ошибка ввода");
    //         }

    //     }
    // }
}