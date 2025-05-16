package org.example.TodoAssignment.models;

import org.example.TodoAssignment.sequencers.PersonIdSequencer;

import java.util.Objects;

public class Person{
    private int id;
    private String firstName;
    private String lastName;


    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }


    public Person(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            throw new IllegalArgumentException("Last name cannot be Null or Empty");
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("First name cannot be Null or Empty");

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getId() == person.getId() && Objects.equals(getFirstName(), person.getFirstName()) && Objects.equals(getLastName(), person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append("\n").append("Name: ").append(getFirstName()).append(" ").append(getLastName()).append("\n");

        return sb.toString();
    }
}
