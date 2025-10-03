package edu.finalproject.model;

public class PersonalData {

    Long id;
    String firstName;
    String lastName;

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d)", firstName, lastName, id);
    }
}

