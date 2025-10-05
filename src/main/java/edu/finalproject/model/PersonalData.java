package edu.finalproject.model;

public class PersonalData implements NumericField {

    Long id;
    String firstName;
    String lastName;

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d)", firstName, lastName, id);
    }

    @Override
    public long getNumericValue() {

        return id;
    }

    //добавил геттеры для бинарного поиска
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
}

