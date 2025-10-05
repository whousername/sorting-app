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

    public static String getHeader(int userCount) {
        return String.format("=== Список пользователей " +
                "===%nВсего пользователей: %d%n----------------------------------------%n", userCount);
    }
    
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
            
            UserValidator.validateUsername(firstName);
            UserValidator.validateUsername(lastName);
            
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

    public static long getMaxIdFromLines(List<String> lines) {
        long maxId = 0;
        Pattern ID_PATTERN = Pattern.compile("\\(ID:\\s*(\\d+)\\)");

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("USER:")) {
                try {
                    // Пытаемся извлечь ID напрямую из строки
                    Matcher idMatcher = ID_PATTERN.matcher(line);
                    if (idMatcher.find()) {
                        long id = Long.parseLong(idMatcher.group(1));
                        if (id > maxId) {
                            maxId = id;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка парсинга ID из строки: " + line + " - " + e.getMessage());
                }
            }
        }

        return maxId;
    }

}
