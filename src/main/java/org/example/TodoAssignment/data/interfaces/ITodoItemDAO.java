package org.example.TodoAssignment.data.interfaces;

import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;

import java.time.LocalDate;
import java.util.List;

public interface ITodoItemDAO extends IDAO<TodoItem> {

    List<TodoItem> findByDoneStatus(boolean done);

    List<TodoItem> findByAssignee(int personId);

    List<TodoItem> findByAssignee(Person person);

    List<TodoItem> findByUnassignedTodoItems();

}
