package edu.finalproject.insertOutput;

public class WarningColors {
    private static final String RESET = "\u001B[0m";
    private static final String COLOR_SUCCESS = "\u001B[32m";
    private static final String COLOR_WARNING = "\u001B[33m";
    private static final String COLOR_ERROR = "\u001B[31m";

    public static void printStatus(Status status, String massage){
        String formattedMessage = formatStatusMessage(status, massage);
        System.out.println(formattedMessage);
    }

    public static String getStatus(Status status, String massage) {
        return formatStatusMessage(status, massage);
    }

    private static String formatStatusMessage(Status status, String massage){
        String color;
        String prefix;

        switch (status){
            case SUCCESS -> {
                color = COLOR_SUCCESS;
                prefix = " [Успешно]: ";
            }
            case WARNING -> {
                color = COLOR_WARNING;
                prefix = "[Внимание]";
            }
            case ERROR -> {
                color = COLOR_ERROR;
                prefix = "[Ошибка]";
            }
            default -> {
                color = RESET;
                prefix = "[INFO] ";
            }
        }

        return String.format("%s %s %s %s\n", color, prefix, massage, RESET);
    }
}
