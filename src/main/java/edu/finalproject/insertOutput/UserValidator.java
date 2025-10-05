<<<<<<<< HEAD:src/main/java/edu/finalproject/inputOutput/userValidator.java
package edu.finalproject.inputOutput;
========
package edu.finalproject.insertOutput;
>>>>>>>> integration:src/main/java/edu/finalproject/insertOutput/UserValidator.java

public class UserValidator {
    private static final String USERNAME_PATTERN = "[a-zA-Zа-яА-ЯёЁ]{3,20}$";

    public static void validateUsername(String name){
        if (name == null || !name.matches(USERNAME_PATTERN)){
            throw new IllegalArgumentException(
                    WarningColors.getStatus(Status.ERROR, "Некорректное имя: "
                            + name + " (допустимы буквы, длина 3–20)")
            );
        }
    }
}
