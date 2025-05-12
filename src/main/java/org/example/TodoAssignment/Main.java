package org.example.TodoAssignment;

import org.example.TodoAssignment.data.AppUserDAOCollection;
import org.example.TodoAssignment.data.PersonDAOCollection;
import org.example.TodoAssignment.data.TodoItemDAOCollection;
import org.example.TodoAssignment.data.TodoItemTaskDAOCollection;
import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.seeddata.SeedData;


public class Main {
    public static void main(String[] args) {

        PersonDAOCollection personDAO = new PersonDAOCollection();
        AppUserDAOCollection userDAO = new AppUserDAOCollection();
        TodoItemDAOCollection todoItemDAO = new TodoItemDAOCollection();
        TodoItemTaskDAOCollection todoItemTaskDAO = new TodoItemTaskDAOCollection();

//        SeedData.populate(personDAO, userDAO, todoItemDAO, todoItemTaskDAO);
//        personDAO.create(new Person("Gentrit", "Hoti"));
        System.out.println(" " + personDAO.findAll());
//        System.out.println(todoItemDAO.findByAssignee(2));
        System.out.println("Search by Id: 14");
        System.out.println("Person found: " + personDAO.findById(14));
        System.out.println("--------------------------------------------");

        System.out.println("Serach by name Gentrit");
        System.out.println("Names: " + personDAO.findByName("Gentrit"));
    }
}
