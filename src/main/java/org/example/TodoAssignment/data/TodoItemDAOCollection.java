package org.example.TodoAssignment.data;

import org.example.TodoAssignment.data.interfaces.ITodoItemDAO;
import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;
import org.example.TodoAssignment.models.TodoItemTask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements ITodoItemDAO {
    private final List<TodoItem> todoItemList;

    public TodoItemDAOCollection() {
        this.todoItemList = new ArrayList<>();
    }

    List<TodoItem> getTodoItemList() {
        return todoItemList;
    }

    @Override
    public TodoItem create(TodoItem todoItem) {
        getTodoItemList().add(todoItem);
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {
        return getTodoItemList();
    }

    @Override
    public TodoItem findById(int id) {
        for (TodoItem todoItem : getTodoItemList()) {
            if (todoItem.getId() == id) {
                return todoItem;
            }
        }
        return null;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> listByStatusDone = new ArrayList<>();
        for (TodoItem todoItem : getTodoItemList()) {
            if (todoItem.isDone() == done) {
                listByStatusDone.add(todoItem);
            }
        }
        return listByStatusDone;
    }

    @Override
    public List<TodoItem> findByAssignee(int personId) {
        List<TodoItem> todoItemsByPersonId = new ArrayList<>();
        for (TodoItem todoItem : getTodoItemList()) {
            if (todoItem.getCreator().getId() == personId) {
                todoItemsByPersonId.add(todoItem);
            }
        }
        return todoItemsByPersonId;
    }

    @Override
    public List<TodoItem> findByAssignee(Person person) {
        List<TodoItem> todoItemsByPersonId = new ArrayList<>();
        for (TodoItem todoItem : getTodoItemList()) {
            if (todoItem.getCreator().getId() == person.getId()) {
                todoItemsByPersonId.add(todoItem);
            }
        }
        return todoItemsByPersonId;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItems() {
        return getTodoItemList().stream().filter((p) -> p.getCreator() == null || p.getCreator().equals(" ")).
                collect(Collectors.toList());
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        for (int i = 0; i < getTodoItemList().size(); i++) {
            if (getTodoItemList().get(i).getId() == todoItem.getId()) {
                getTodoItemList().set(i, todoItem);
                return todoItem;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        getTodoItemList().removeIf((todoItem) -> todoItem.getId() == id);
    }
}
