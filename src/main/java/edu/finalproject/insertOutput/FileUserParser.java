package edu.finalproject.insertOutput;

import java.util.Arrays;

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;

public class FileUserParser {

    public static PersonalData parseLine(String line, long idCounter) {
        line = line.trim();
        if (!line.startsWith("USER:")) {
            return null;
        }

        try {
            String namePart = line.substring(5).trim(); 

            int idIndex = namePart.indexOf("(ID:");
            if (idIndex == -1) throw new IllegalArgumentException("Не найден ID");

            String fullName = namePart.substring(0, idIndex).trim(); 
            String[] parts = fullName.split(" ");
            if (parts.length < 2) throw new IllegalArgumentException("Не найдено имя или фамилия");

            String firstName = parts[0];
            String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));

            String idPart = namePart.substring(idIndex + 4, namePart.indexOf(")", idIndex)).trim();
            long id = Long.parseLong(idPart);

            return new DtoBuilder().id(idCounter).firstName(firstName).lastName(lastName).build();

        } catch (Exception e) {
            System.err.println("Ошибка парсинга строки: \"" + line + "\" - " + e.getMessage());
            return null;
        }
    }
}