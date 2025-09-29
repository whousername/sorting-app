package edu.finalproject.model;

import java.util.Scanner;

public class ConsoleBuilder implements Builder{

    PersonalData personalData = new PersonalData(null, null, null);
    Scanner scanner = new Scanner(System.in);

    @Override
    public Builder id(Long dummy) {

        System.out.println("\nПожалуйста, введите ID:\n");
        Long id = Long.valueOf(scanner.nextLine());
        validateValue(id);
        personalData.id = id;
        return this;
    }

    @Override
    public Builder firstName(String dummy) {

        System.out.println("\nПожалуйста, введите имя:\n");
        String firstName = scanner.nextLine();
        validateValue(firstName);
        personalData.firstName = firstName;
        return this;
    }


    @Override
    public Builder lastName(String dummy) {

        System.out.println("\nПожалуйста, введите фамилию:\n");
        String lastName = scanner.nextLine();
        validateValue(lastName);
        personalData.lastName = lastName;
        return this;
    }

    @Override
    public PersonalData build() {

        scanner.close();
        validateValue(personalData.id);
        validateValue(personalData.firstName);
        validateValue(personalData.lastName);
        return personalData;
    }

    private void validateValue(String value) {

        if (value == null || value.trim().isEmpty()) throw new IllegalStateException("Cannot be null");
    }

    private void validateValue(Long value) {

        if (value == null) throw new IllegalStateException("Cannot be null");
    }
}
