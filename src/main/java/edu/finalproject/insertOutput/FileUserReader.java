package edu.finalproject.insertOutput;

import edu.finalproject.model.DtoBuilder;
import edu.finalproject.model.PersonalData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUserReader {
    
    // Регулярное выражение для парсинга: USER: Имя Фамилия (ID: 123)
    private static final Pattern USER_PATTERN = Pattern.compile("USER:\\s+(.+?)\\s+\\(ID:\\s*(\\d+)\\)");
    
    /**
     * Парсит строку в формате "имя,фамилия" (старый формат)
     */
    // public static PersonalData parseLine(String line, long idCounter) {
    //     String[] parts = line.split(",");
    //     if (parts.length != 2) {
    //         throw new IllegalArgumentException("Неверное количество полей. Ожидается 2, получено " + parts.length);
    //     }

    //     try {
    //         String firstName = parts[0].trim();
    //         userValidator.validateUsername(firstName);

    //         String lastName = parts[1].trim();
    //         userValidator.validateUsername(lastName);

    //         return new DtoBuilder().id(idCounter).firstName(firstName).lastName(lastName).build();
    //     } catch (Exception e) {
    //         throw new IllegalArgumentException("Ошибка при разборе данных: " + e.getMessage(), e);
    //     }
    // }
    
    /**
     * ПАРСЕР СТРОКИ ФОРМАТ "USER: Имя Фамилия (ID: 123)" возвращает объект
     */
    public static PersonalData parseUserLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        Matcher matcher = USER_PATTERN.matcher(line.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Неверный формат строки пользователя: " + line);
        }
        
        try {
            String fullName = matcher.group(1).trim(); // "Имя Фамилия"
            long id = Long.parseLong(matcher.group(2)); // ID
            
            // Разделяем имя и фамилию
            String[] nameParts = fullName.split("\\s+", 2);
            if (nameParts.length != 2) {
                throw new IllegalArgumentException("Неверный формат имени: " + fullName);
            }
            
            String firstName = nameParts[0].trim();
            String lastName = nameParts[1].trim();
            
            userValidator.validateUsername(firstName);
            userValidator.validateUsername(lastName);
            
            return new DtoBuilder().id(id).firstName(firstName).lastName(lastName).build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при разборе данных пользователя: " + e.getMessage(), e);
        }
    }
    
    /**
     * МЕТОД ПАРСЕР ВСЕГО ФАЙЛА возвращает список пользователей
     */
    public static List<PersonalData> parseSavedFile(List<String> lines) {
        List<PersonalData> users = new ArrayList<>();
        
        for (String line : lines) {
            line = line.trim();
            
            // Пропускаем пустые строки, заголовки и разделители
            if (line.isEmpty() || 
                line.equals("=== Список пользователей ===") ||
                line.startsWith("=== Дополнительные пользователи ===") ||
                line.startsWith("Всего пользователей:") || 
                line.equals("----------------------------------------")) {
                continue;
            }
            
            // Парсим строки с пользователями
            if (line.startsWith("USER:")) {
                try {
                    PersonalData user = parseUserLine(line);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка парсинга строки: " + line + " - " + e.getMessage());
                }
            }
        }
        
        return users;
    }
}
