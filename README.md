# Sorting application

Приложение для демонстрации различных алгоритмов сортировки и бинарного поиска.

## Структура проекта

```
src/main/java/com/sortingapp/
├── Main.java                   # Главный класс приложения
├── model/                      # Модели данных и паттерн Builder
├── sortAlgorithms/             # Алгоритмы сортировки
├── search/                     # Алгоритмы бинарного поиска
├── UI/                         # Утилиты для работы с UI
└── insertOutput/               # Ввод/вывод данных
```

## Требования

- Java 25
- Maven 3.14.0

## Сборка и запуск

```bash
# Сборка проекта
mvn clean compile

# Запуск приложения
mvn exec:java -Dexec.mainClass="edu.finalproject.Main"

# Запуск тестов
mvn test
```

# Пример использования dtoBuilder:
```java
Builder builder = new dtoBuilder();  
PersonalData personalData = builder.id(1L).firstName("Vasyliy").lastName("Pupkin").build();
System.out.println(personalData);
```
# Пример использования consoleBuilder:
```java
Builder builder = new ConsoleBuilder();  
PersonalData personalData = builder.id(0L).firstName("").lastName("").build();
System.out.println(personalData);
```
## Пакеты

- **model** - содержит модели данных и реализацию паттерна Builder
- **sortAlgorithms** - различные алгоритмы сортировки (QuickSort, MergeSort, BubbleSort и др.)
- **search** - алгоритмы бинарного поиска
- **UI** - утилиты для работы с UI
- **inputOutput** - классы для работы с вводом/выводом данных
