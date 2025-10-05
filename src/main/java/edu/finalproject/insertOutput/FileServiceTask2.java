package edu.finalproject.insertOutput;

import java.util.List;
import java.util.Scanner;

import edu.finalproject.model.PersonalData;

public class FileServiceTask2 {

    private Scanner scan = new Scanner(System.in);
    private InsertOutput insertOutput = new InsertOutput();

      //доп задание №2
      
      public void processUsers(List<PersonalData> users) {
        System.out.println("\nПолучено пользователей: " + users.size());

        if (users.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        //сохранения файла
        if (askUserToSaveFile()) {
            System.out.print("Сохранить пользователей в отдельный файл в формате .txt" +
                    " или перезаписать существующий (c/r):");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("c")) {
                System.out.println("Назовите файл");
                String fileName = scan.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users);
            }

            else if (choice.equalsIgnoreCase("r")) {
                System.out.println("Напишите название файла");
                String fileName = scan.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users, true);
            }else{
                System.out.println("Ошибка ввода");
            }

        }
    }
    
    //добавлена валидация на Y и N
    private boolean askUserToSaveFile() {
        while (true) {
            System.out.println("\nСоздать файл с результатами? (y/n): ");
            String input = scan.nextLine().trim();
            
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Неверный ввод! Пожалуйста, введите 'y' или 'n'");
            }
        }
    }
}
