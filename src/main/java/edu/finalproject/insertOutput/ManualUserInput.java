package edu.finalproject.insertOutput;

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;

import java.util.Scanner;

public class ManualUserInput {
    private static long idCounter = 0;

    static int readPositiveInt(Scanner scanner) {
        while (true) {
            try {
                int n = Integer.parseInt(scanner.nextLine());
                if (n <= 0) throw new IllegalArgumentException();
                return n;
            } catch (Exception e) {
                WarningColors.printStatus(Status.WARNING, "Введите положительное число!");
            }
        }
    }

    public static PersonalData readUserFromConsole(Scanner scanner) {
        DtoBuilder builder = new DtoBuilder();

        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        UserValidator.validateUsername(firstName);

        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        UserValidator.validateUsername(lastName);

        return builder.id(idCounter++)
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
