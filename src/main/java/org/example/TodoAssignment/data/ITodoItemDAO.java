package org.example.TodoAssignment.data;

import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;

import java.util.List;

public interface ITodoItemDAO extends IDAO<TodoItem> {

    List<TodoItem> findByDoneStatus(boolean done);

    List<TodoItem> findByAssignee(int personId);

    List<TodoItem> findByAssignee(Person person);

    List<TodoItem> findByUnassignedTodoItems();

}
