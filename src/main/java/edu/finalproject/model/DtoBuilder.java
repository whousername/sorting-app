package edu.finalproject.model;

public class DtoBuilder implements Builder {

    PersonalData personalData = new PersonalData(null, null, null);

    @Override
    public Builder id(Long id) {

        validateValue(id);
        personalData.id = id;
        return this;
    }

    @Override
    public Builder firstName(String firstName) {

        validateValue(firstName);
        personalData.firstName = firstName;
        return this;
    }


    @Override
    public Builder lastName(String lastName) {

        validateValue(lastName);
        personalData.lastName = lastName;
        return this;
    }

    @Override
    public PersonalData build() {

        validateValue(personalData.id);
        validateValue(personalData.firstName);
        validateValue(personalData.lastName);
        return personalData;
    }

    private void validateValue(String value) {

        if (value == null || value.trim().isEmpty()) throw new IllegalStateException("Cannot be null");
    }

    private void validateValue(Long value) {

        if (value == null) throw new IllegalStateException("Cannot be null");
    }
}
