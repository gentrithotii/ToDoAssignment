package org.example.TodoAssignment.data;

import org.example.TodoAssignment.data.interfaces.ITodoItemDAO;
import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.models.TodoItem;
import org.example.TodoAssignment.models.TodoItemTask;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements ITodoItemDAO {
    private Connection connection;

    public TodoItemDAOCollection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TodoItem create(TodoItem todoItem) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) Values (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todoItem.getTitle());
            ps.setString(2, todoItem.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            ps.setBoolean(4, todoItem.isDone());
            if ((Integer) todoItem.getCreator().getId() != null) {
                ps.setInt(5, todoItem.getCreator().getId());
            }
//            ps.setInt(5, 0);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet key = ps.getGeneratedKeys();
                while (key.next()) {
                    int idKey = key.getInt(1);
                    return new TodoItem(idKey, todoItem.getTitle(), todoItem.getDescription(), todoItem.getDeadLine(), todoItem.isDone());
                }
            }

        } catch (SQLException e) {
            System.err.println("Something went wrong with the connection" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<TodoItem> findAll() {
        List<TodoItem> todoItemList = new ArrayList<>();
        String query = "SELECT todo_id, title, description, deadline, done, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti " +
                "LEFT JOIN person p ON ti.assignee_id = p.person_id; ";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)
        ) {
            while (rs.next()) {

                int todoId = rs.getInt("todo_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                boolean status = rs.getBoolean("done");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int assigneeId = rs.getInt("person_id");
                String personFirstName = rs.getString("first_name");
                String personLastName = rs.getString("last_name");

                TodoItem todoItem;
                if (assigneeId == 0) {
                    todoItem = new TodoItem(todoId, title, description, deadline, status);

                }
                else {
                    todoItem = new TodoItem(todoId, title, description, deadline, status, new Person(assigneeId, personFirstName, personLastName));
                }
                todoItemList.add(todoItem);

            }
            return todoItemList;
        } catch (SQLException e) {
            System.err.println("Error connecting to SQL: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public TodoItem findById(int id) {

        return null;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> listByStatusDone = new ArrayList<>();

        return listByStatusDone;
    }

    @Override
    public List<TodoItem> findByAssignee(int personId) {
        List<TodoItem> todoItemsByPersonId = new ArrayList<>();

        return todoItemsByPersonId;
    }

    @Override
    public List<TodoItem> findByAssignee(Person person) {
        List<TodoItem> todoItemsByPersonId = new ArrayList<>();

        return todoItemsByPersonId;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItems() {
        List<TodoItem> byUnassignedTodos = new ArrayList<>();

        return byUnassignedTodos;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return true;
    }
}
