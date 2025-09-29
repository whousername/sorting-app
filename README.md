# sorting aplication

Приложение для демонстрации различных алгоритмов сортировки и бинарного поиска.

## Структура проекта

```
src/main/java/com/sortingapp/
├── Main.java                    # Главный класс приложения
├── modelAndBuilder/            # Модели данных и паттерн Builder
├── sortAlgorithms/             # Алгоритмы сортировки
├── binarySearch/               # Алгоритмы бинарного поиска
├── validationUtils/            # Утилиты для валидации
└── inputOutput/                # Ввод/вывод данных
```

## Требования

- Java 11 или выше
- Maven 3.6 или выше

## Сборка и запуск

```bash
# Сборка проекта
mvn clean compile

# Запуск приложения
mvn exec:java -Dexec.mainClass="edu.finalproject.Main"

# Запуск тестов
mvn test
```

## Пакеты

- **modelAndBuilder** - содержит модели данных и реализацию паттерна Builder
- **sortAlgorithms** - различные алгоритмы сортировки (QuickSort, MergeSort, BubbleSort и др.)
- **binarySearch** - алгоритмы бинарного поиска
- **validationUtils** - утилиты для валидации входных данных
- **inputOutput** - классы для работы с вводом/выводом данных
