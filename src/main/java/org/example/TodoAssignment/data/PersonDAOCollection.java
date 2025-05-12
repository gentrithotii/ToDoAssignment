package org.example.TodoAssignment.data;

import org.example.TodoAssignment.data.interfaces.IPersonDAO;
import org.example.TodoAssignment.models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAOCollection implements IPersonDAO {
   private final List<Person> personList;

    public PersonDAOCollection() {
        this.personList = new ArrayList<>();
    }

    private List<Person> getPersonList() {
        return personList;
    }

    @Override
    public Person persist(Person person) {
        if (person != null) {
            getPersonList().add(person);
            return person;
        }
        throw new IllegalArgumentException("Can't leave empty");
    }

    @Override
    public Person findById(int id) {
        for (Person person : getPersonList()) {
            if (person.getId() == id) {
                return person;
            }
        }
        System.out.println("Person not found ");
        return null;
    }

    @Override
    public Person findByEmail(String email) {
        for (Person person : getPersonList()) {
            if (person.getEmail().equalsIgnoreCase(email)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        return getPersonList();
    }

    @Override
    public void remove(int id) {
        getPersonList().removeIf((person) -> person.getId() == id);
    }
}
