package edu.finalproject.model;

public class FileUserReader {
    public static PersonalData parseLine(String line, long idCounter) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Неверное количество полей. Ожидается 2, получено " + parts.length);
        }

        try {
            String firstName = parts[0].trim();
            userValidator.validateUsername(firstName);

            String lastName = parts[1].trim();
            userValidator.validateUsername(lastName);

            return new DtoBuilder().id(idCounter).firstName(firstName).lastName(lastName).build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при разборе данных: " + e.getMessage(), e);
        }
    }
}
