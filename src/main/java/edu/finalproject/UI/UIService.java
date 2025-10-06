package edu.finalproject.UI;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.finalproject.insertOutput.FileService;
import edu.finalproject.insertOutput.InsertOutput;
import edu.finalproject.insertOutput.Status;
import edu.finalproject.insertOutput.WarningColors;
import edu.finalproject.model.PersonalData;
import edu.finalproject.search.SearchEngine;
import edu.finalproject.sortAlgorithms.BubbleSort;
import edu.finalproject.sortAlgorithms.CustomUserCollection;
import edu.finalproject.sortAlgorithms.SelectionSort;
import edu.finalproject.sortAlgorithms.SortAlgorithms;
import edu.finalproject.sortAlgorithms.SortStrategy;

public class UIService {

    private final Scanner scan = new Scanner(System.in);

    private final InsertOutput insertOutput = new InsertOutput();

    private final FileService fileService = new FileService();

    private CustomUserCollection<PersonalData> users = new CustomUserCollection<>();

    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Выберите действие: ");
            running = handleMainChoice(choice);
            pressEnterToContinue();
        }
    }

    private void printMainMenu() {
        System.out.println("= = =  П Р И Л О Ж Е Н И Е  Д Л Я  С О Р Т И Р О В К И  = = =");
        System.out.println("МЕНЮ:");
        System.out.println("1. Выбрать способ заполнения");
        System.out.println("2. Показать текущую коллекцию");
        System.out.println("3. Сортировка коллекции");
        System.out.println("4. Бинарный поиск");
        System.out.println("5. Сохранить текущую коллекцию в файл");
        System.out.println("6. Выход");
    }

    private boolean handleMainChoice(int choice) {
        switch (choice) {
            case 1 -> handleFillingMenu();

            case 2 -> insertOutput.displayUsers(users);

            case 3 -> handleSortingUniversalSort(users);

            case 4 -> handleBinarySearchService();

            case 5 -> fileService.processUsers(users);

            case 6 -> {
                exit();
                return false;
            }

            default -> System.out.println("Неверный выбор. Попробуйте снова.");
        }
        return true;
    }

    private <E> Comparator<E> chooseComparator(CustomUserCollection<PersonalData> users) {
        System.out.println("Выберите поле для сортировки: ");
        System.out.println("1. ID");
        System.out.println("2. Имя");
        System.out.println("3. Фамилия");
        int choice = readInt("Выберите опцию: ");
        return switch (choice) {
            case 1 -> (Comparator<E>) Comparator.comparing(u -> ((PersonalData) u).getId());
            case 2 -> (Comparator<E>) Comparator.comparing(u -> ((PersonalData) u).getFirstName());
            case 3 -> (Comparator<E>) Comparator.comparing(u -> ((PersonalData) u).getLastName());
            default -> {
                System.out.println("Неверный выбор, сортировка по умолчанию (ID)!");
                yield (Comparator<E>) Comparator.comparing(u -> ((PersonalData) u).getId());
            }
        };
    }

    private <E> void handleSortingUniversalSort(CustomUserCollection<PersonalData> users) {
        if (users == null) {
            System.out.println("Коллекция пуста. Сначала заполните ее!");
            return;
        }
        System.out.println("Выберите тип сортировки: ");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Сортировка выбором");

        int choice = readInt("Выберите опцию: ");

        SortStrategy<E> strategy;

        switch (choice) {
            case 1 -> strategy = new BubbleSort<>();
            case 2 -> strategy = new SelectionSort<>();
            default -> {
                System.out.println("Неверный выбор!");
                return;
            }
        }
        Comparator<E> comparator = chooseComparator((CustomUserCollection<PersonalData>) users);

        SortAlgorithms<E> sorter = new SortAlgorithms<>(strategy);
        sorter.sort((CustomUserCollection<E>) users, comparator);
        System.out.println("Коллекция отсортирована!");
    }

    private void handleFillingMenu() {
        System.out.println(" СПОСОБЫ ЗАПОЛНЕНИЯ:");
        System.out.println("1. Ручное заполнение");
        System.out.println("2. Заполнить случайными данными");
        System.out.println("3. Заполнить из файла");
        System.out.println("4. Назад");

        int choice = readInt("Выберите опцию: ");
        switch (choice) {
            case 1 -> {
                users = insertOutput.manualInput();
                insertOutput.displayUsers(users);
            }
            case 2 -> {
                users = insertOutput.generateRandomData();
                WarningColors.printStatus(Status.SUCCESS, "Список пользователей успешно создан!");
                insertOutput.displayUsers(users);
            }
            case 3 -> {
                System.out.print("Укажите название файла: ");
                String filename = readString();
                users = insertOutput.readFromSavedFile(filename + ".txt"); //пришлось исправить ибо тут было не правильное
                // название метода
                insertOutput.displayUsers(users);
            }
            case 4 -> {
                System.out.println("Возврат в главное меню...");
            }
            default -> {
                System.out.println("Неверный выбор!");
            }
        }
    }

    private void handleBinarySearchService() {
        System.out.println("ОПЦИИ ПОИСКА: ");
        System.out.println("1. Поиск по ID");
        System.out.println("2. Поиск по имени");
        System.out.println("3. Поиск по фамилии");
        System.out.println("4. Назад");

        int choice = readInt("Выберете параметр поиска: ");
        switch (choice) {
            case 1 -> binarySearchGeneric(PersonalData::getId, "Введите ID: ", Long::valueOf);
            case 2 -> binarySearchGeneric(PersonalData::getFirstName, "Введите имя: ", String::trim);
            case 3 -> binarySearchGeneric(PersonalData::getLastName, "Введите фамилию: ", String::trim);
            case 4 -> System.out.println("Возврат в главное меню");
            default -> System.out.println("Неверный выбор");
        }
    }

    private <F extends Comparable<? super F>> void binarySearchGeneric(
            Function<PersonalData, F> getter,
            String promptMessage,
            Function<String, F> parser) {
        //временный List из users для метода .sort()
        //заполнил стримом, даже не спрашивайте почему
        List<PersonalData> sortedUsers = users.stream()
                .sorted(Comparator.comparing(getter))
                .collect(Collectors.toList());

        sortedUsers.sort(Comparator.comparing(getter));

        SearchEngine<PersonalData, F> binarySearch = new SearchEngine<>(sortedUsers, getter, Comparator.naturalOrder());

        System.out.print(promptMessage);
        String inputRaw = readString();

        try {
            F key = parser.apply(inputRaw.trim());
            PersonalData found = binarySearch.find(key);
            if (found != null) {
                System.out.println("Пользователь найден: " + found);
            } else {
                System.out.println("Пользователь не найден");
            }
        } catch (RuntimeException e) {
            System.out.println("Неверный формат ввода: " + e.getMessage());
        }
    }

    private void exit() {
        if (!users.isEmpty()) {
            System.out.println("В вашей коллекции имеются несохраненные данные..." + users.size() + " пользователей.");
            System.out.println("Вы уверенны что хотите выйти? д/н");
            String answer = readString();
            if (answer.equalsIgnoreCase("д")) {
                System.out.println("До свидания!");
            } else if (answer.equalsIgnoreCase("н")) {
                fileService.processUsers(users);
            } else {
                System.out.println("Неверный ввод! Пожалуйста, введите 'д' или 'н'");
            }
        } else {
            System.out.println("До свидания!");
        }
    }

    private String readString() {
        while (!scan.hasNextLine()) {
            System.out.println("Введите строку корректно");
            scan.nextLine();
        }
        return scan.nextLine();
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("Введите корректное число!");
        }
        int i = scan.nextInt();
        scan.nextLine();
        System.out.println();
        return i;
    }

    public void pressEnterToContinue() {
        System.out.println("\nНажмите Enter, чтобы продолжить...");
        scan.nextLine();
    }
}