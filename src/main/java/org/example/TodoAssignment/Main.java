package org.example.TodoAssignment;

import org.example.TodoAssignment.data.AppUserDAOCollection;
import org.example.TodoAssignment.data.PersonDAOCollection;
import org.example.TodoAssignment.data.TodoItemDAOCollection;
import org.example.TodoAssignment.data.TodoItemTaskDAOCollection;
import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;
import org.example.TodoAssignment.seeddata.SeedData;
import org.example.TodoAssignment.utils.DBConnection;

import java.sql.SQLException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        try {
            PersonDAOCollection personDAO = new PersonDAOCollection(DBConnection.getInstance().getConnection());
            AppUserDAOCollection userDAO = new AppUserDAOCollection();
            TodoItemDAOCollection todoItemDAO = new TodoItemDAOCollection(DBConnection.getInstance().getConnection());
            TodoItemTaskDAOCollection todoItemTaskDAO = new TodoItemTaskDAOCollection();

//        SeedData.populate(personDAO, userDAO, todoItemDAO, todoItemTaskDAO);
//        personDAO.create(new Person("Gentrit", "Hoti"));
//            System.out.println(" " + personDAO.findAll());
//        System.out.println(todoItemDAO.findByAssignee(2));
//            System.out.println("Search by Id: 14");
//            System.out.println("Person found: " + personDAO.findById(14));
//            System.out.println("--------------------------------------------");

//            System.out.println("Serach by name Gentrit");
//            System.out.println("Names: " + personDAO.findByName("Gentrit"));
//            Person p = new Person(16, "Testi", "Testson");
//            System.out.println(personDAO.update(p));
//            System.out.println(personDAO.deleteById(16));
//            System.out.println(todoItemDAO.findAll());
//            System.out.println(todoItemDAO.create(new TodoItem("Gentriti", "me ba gentriti", LocalDate.now(), false)));
//            System.out.println(todoItemDAO.findById(100));
//            System.out.println(todoItemDAO.findByDoneStatus(true));
//            System.out.println(todoItemDAO.findByAssignee(43));
//            System.out.println(todoItemDAO.findByAssignee(new Person(43, "Paula", "Lee")));
//            System.out.println(todoItemDAO.findByUnassignedTodoItems());
            System.out.println(todoItemDAO.update(new TodoItem(115, "Clean the pc fans", "Importan to do it fast", LocalDate.of(2025,05, 20), false, new Person(43, "Paula", "Lee"))));
        } catch (SQLException e) {
            System.err.println("Could not connect to server" + e.getMessage());
        }
    }
}
