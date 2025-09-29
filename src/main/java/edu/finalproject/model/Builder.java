package edu.finalproject.model;

public interface Builder {

    Builder id(Long id);

    Builder firstName(String firstName);

    Builder lastName(String lastName);

    PersonalData build();
}
