package org.example.TodoAssignment.data;

import org.example.TodoAssignment.models.Person;

import java.util.List;

public interface IPersonDAO extends IDAO<Person> {

    List<Person> findByName(String name);

    boolean testUpdateForCommit(Person person);
}
