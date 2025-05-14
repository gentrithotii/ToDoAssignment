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
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todoItem.getTitle());
            ps.setString(2, todoItem.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            ps.setBoolean(4, todoItem.isDone());

            if (todoItem.getCreator() == null || todoItem.getCreator().getId() == 0) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, todoItem.getCreator().getId());
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet key = ps.getGeneratedKeys()) {
                    if (key.next()) {
                        int idKey = key.getInt(1);
                        return new TodoItem(idKey, todoItem.getTitle(), todoItem.getDescription(), todoItem.getDeadLine(), todoItem.isDone(), todoItem.getCreator());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating Todo item " + e.getMessage());
            e.getStackTrace();
        }

        return null;
    }

    @Override
    public List<TodoItem> findAll() {
        List<TodoItem> todoItemList = new ArrayList<>();
        String query = "SELECT todo_id, title, description, deadline, done, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti " +
                "LEFT JOIN person p ON ti.assignee_id = p.person_id; ";
        try (
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query)
        ) {
            while (rs.next()) {
                int todoId = rs.getInt("todo_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                boolean status = rs.getBoolean("done");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int personId = rs.getInt("person_id");
                boolean hasAssignee = !rs.wasNull();
                String personFirstName = rs.getString("first_name");
                String personLastName = rs.getString("last_name");

                TodoItem todoItem;
                if (!hasAssignee) {
                    todoItem = new TodoItem(todoId, title, description, deadline, status);

                } else {
                    todoItem = new TodoItem(todoId, title, description, deadline, status, new Person(personId, personFirstName, personLastName));
                }
                todoItemList.add(todoItem);

            }
            return todoItemList;
        } catch (SQLException e) {
            System.err.println("Error fetching all the people from the db " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public TodoItem findById(int id) {
        String sql = "SELECT ti.todo_id, ti.title, ti.description, ti.deadline, ti.done, ti.assignee_id, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id " +
                "WHERE ti.todo_id = ? ";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TodoItem(
                            rs.getInt("todo_id"), rs.getString("title"),
                            rs.getString("description"), rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            new Person(
                                    rs.getInt("person_id"),
                                    rs.getString("first_name"), rs.getString("last_name")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding by id! " + e.getMessage());
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> listByDoneStatus = new ArrayList<>();
        String sql = "SELECT ti.todo_id, ti.title, ti.description, ti.deadline, ti.done, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti " +
                "LEFT JOIN person p ON ti.assignee_id = p.person_id " +
                "WHERE ti.done = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, done);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    rs.getInt("person_id");
                    boolean hasAssignee = !rs.wasNull();

                    if (!hasAssignee) {

                        listByDoneStatus.add(new TodoItem(
                                rs.getInt("todo_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getDate("deadline").toLocalDate(),
                                rs.getBoolean("done")
                        ));
                    } else {
                        listByDoneStatus.add(new TodoItem(rs.getInt("todo_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getDate("deadline").toLocalDate(),
                                rs.getBoolean("done"),
                                new Person(
                                        rs.getInt("person_id"),
                                        rs.getString("first_name"),
                                        rs.getString("last_name")
                                )
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding all by done status " + e.getMessage());
        }
        return listByDoneStatus;
    }

    @Override
    public List<TodoItem> findByAssignee(int personId) {
        List<TodoItem> todoItemsByPersonId = new ArrayList<>();
        String sql = "SELECT ti.todo_id, ti.title, ti.description, ti.deadline, ti.done, ti.assignee_id, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id " +
                "WHERE ti.assignee_id = ? ";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, personId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                     todoItemsByPersonId.add(new TodoItem(
                            rs.getInt("todo_id"), rs.getString("title"),
                            rs.getString("description"), rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            new Person(
                                    rs.getInt("person_id"),
                                    rs.getString("first_name"), rs.getString("last_name"))));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding by id assignee! " + e.getMessage());
            e.getStackTrace();
        }
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
