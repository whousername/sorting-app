package edu.finalproject.model;

public class PersonalData {

    Long id;
    String firstName;
    String lastName;

    public PersonalData(Long id, String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d)", firstName, lastName, id);
    }
}

