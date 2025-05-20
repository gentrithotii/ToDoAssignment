package org.example.TodoAssignment.data.interfaces;

import org.example.TodoAssignment.models.Person;

import java.util.List;

public interface IPersonDAO extends IDAO<Person> {

    List<Person> findByName(String name);

    boolean testUpdateForCommit(Person person);
}
