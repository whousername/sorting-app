package edu.finalproject.model.insertOutput;

public class userValidator {
    private static final String USERNAME_PATTERN = "[a-zA-Zа-яА-ЯёЁ]{3,20}$";

    public static void validateUsername(String name){
        if (name == null || !name.matches(USERNAME_PATTERN)){
            throw new IllegalArgumentException(
                    "Некорректное имя: " + name + " (допустимы буквы, длина 3–20)"
            );
        }
    }
}
