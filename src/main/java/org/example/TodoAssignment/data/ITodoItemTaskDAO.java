package org.example.TodoAssignment.data;

import org.example.TodoAssignment.models.TodoItemTask;

import java.util.List;

public interface ITodoItemTaskDAO {
    TodoItemTask persist(TodoItemTask todoItemTask);

    TodoItemTask findById(int id);

    List<TodoItemTask> findAll();

    List<TodoItemTask> findByAssignedStatus(boolean status);

    List<TodoItemTask> findByPersonId(int personId);

    void remove(int id);

}
