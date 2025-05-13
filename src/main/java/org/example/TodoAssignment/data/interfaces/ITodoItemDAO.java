package org.example.TodoAssignment.data.interfaces;

import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;

import java.time.LocalDate;
import java.util.List;

public interface ITodoItemDAO {
    TodoItem create(TodoItem todoItem);

    List<TodoItem> findAll();

    TodoItem findById(int id);

    List<TodoItem> findByDoneStatus(boolean done);

    List<TodoItem> findByAssignee(int personId);

    List<TodoItem> findByAssignee(Person person);

    List<TodoItem> findByUnassignedTodoItems();

    TodoItem update(TodoItem todoItem);

    boolean deleteById(int id);


}
