package edu.finalproject.insertOutput;

import java.util.Scanner;

import edu.finalproject.model.PersonalData;
import edu.finalproject.sortAlgorithms.CustomUserCollection;

public class FileService {

    private Scanner scan = new Scanner(System.in);
    private InsertOutput insertOutput = new InsertOutput();

    //доп задание №2
    public void processUsers(CustomUserCollection<PersonalData> users) {
        System.out.println("Получено пользователей: " + users.size());

        if (users.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        //сохранения файла
        if (askUserToSaveFile()) {
            System.out.print("Сохранить пользователей в отдельный файл в формате .txt" +
                    " или перезаписать существующий (с/п): ");

            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("с")) {
                System.out.print("Укажите название файла: ");
                String fileName = scan.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users);
            } else if (choice.equalsIgnoreCase("п")) {
                System.out.print("Укажите название файла: ");
                String fileName = scan.nextLine();
                insertOutput.saveToFile(fileName + ".txt", users, true);
            } else {
                System.out.println("Ошибка ввода");
            }
        }
    }

    //добавлена валидация на Y и N
    private boolean askUserToSaveFile() {
        while (true) {
            System.out.print("\nСоздать файл с результатами? (д/н): ");
            String input = scan.nextLine().trim();

            if (input.equalsIgnoreCase("д")) {
                return true;
            } else if (input.equalsIgnoreCase("н")) {
                return false;
            } else {
                System.out.println("Неверный ввод! Пожалуйста, введите 'д' или 'н'");
            }
        }
    }
}
