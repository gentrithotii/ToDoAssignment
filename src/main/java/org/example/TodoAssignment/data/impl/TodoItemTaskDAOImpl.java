package org.example.TodoAssignment.data.impl;

import org.example.TodoAssignment.data.ITodoItemTaskDAO;
import org.example.TodoAssignment.models.TodoItemTask;

import java.util.ArrayList;
import java.util.List;

public class TodoItemTaskDAOImpl implements ITodoItemTaskDAO {
    private final List<TodoItemTask> todoItemTaskList;

    public TodoItemTaskDAOImpl() {
        this.todoItemTaskList = new ArrayList<>();
    }

    private List<TodoItemTask> getTodoItemTaskList() {
        return todoItemTaskList;
    }

    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        if (todoItemTask != null) {
            getTodoItemTaskList().add(todoItemTask);
            return todoItemTask;
        }
        return null;
    }

    @Override
    public TodoItemTask findById(int id) {
        for (TodoItemTask todoItemTask : getTodoItemTaskList()) {
            if (todoItemTask.getId() == id) {
                return todoItemTask;
            }
        }
        return null;
    }

    @Override
    public List<TodoItemTask> findAll() {
        return getTodoItemTaskList();
    }

    @Override
    public List<TodoItemTask> findByAssignedStatus(boolean status) {
        List<TodoItemTask> listWithSpecificStatus = new ArrayList<>();

        for (TodoItemTask todoItemTask : getTodoItemTaskList()) {
            if (todoItemTask.getTodoItem().isDone() == status) {
                listWithSpecificStatus.add(todoItemTask);
            }
        }
        return listWithSpecificStatus;
    }

    @Override
    public List<TodoItemTask> findByPersonId(int personId) {
        List<TodoItemTask> specificPersonTodoTask = new ArrayList<>();
        for (TodoItemTask todoItemTask : getTodoItemTaskList()) {
            if (todoItemTask.getAssignee().getId() == personId) {
                specificPersonTodoTask.add(todoItemTask);
            }
        }
        return specificPersonTodoTask;
    }

    public void remove(int id) {
        getTodoItemTaskList().removeIf((todoItemTask) -> todoItemTask.getId() == id);
    }

}
