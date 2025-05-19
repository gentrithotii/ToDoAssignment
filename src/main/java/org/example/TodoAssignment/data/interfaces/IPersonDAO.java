package org.example.TodoAssignment.data.interfaces;

import org.example.TodoAssignment.models.Person;

import java.util.List;

public interface IPersonDAO {
    Person create(Person person);

    List<Person> findAll();

    Person findById(int id);

    List<Person> findByName(String name);

    Person update(Person person);

    boolean deleteById(int id);

    boolean testUpdateForCommit(Person person);
}
