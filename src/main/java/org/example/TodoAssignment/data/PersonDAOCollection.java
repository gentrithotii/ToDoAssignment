package org.example.TodoAssignment.data;

import org.example.TodoAssignment.data.interfaces.IPersonDAO;
import org.example.TodoAssignment.models.Person;
import org.example.TodoAssignment.utils.DBConnection;
import org.junit.platform.commons.function.Try;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonDAOCollection implements IPersonDAO {
    private Connection connection;


    public PersonDAOCollection(Connection connection) {
        this.connection = connection;
    }

    private List<Person> getPersonList() {
        return findAll();
    }

    @Override
    public Person create(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Can't leave empty");
        }

        String sql = "INSERT INTO person(first_name, last_name) VALUES(?, ?)";
        System.out.println(sql);
        try (
                PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {

            st.setString(1, person.getFirstName());
            st.setString(2, person.getLastName());

            st.executeUpdate();
            getPersonList().add(person);

        } catch (SQLException e) {
            System.err.println("Error Adding the Person" + e.getMessage());
            e.getStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        String query = "SELECT person_id, first_name, last_name FROM person";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int personId = rs.getInt("person_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                Person personToAdd = new Person(personId, firstName, lastName);
                personList.add(personToAdd);

            }
            return personList;
        } catch (SQLException e) {
            System.err.println("Error connecting to SQL: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person p WHERE p.person_id =  " + id;
        try (
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ) {
            if (rs.next()) {
                return new Person(rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name"));
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Can't connect " + e.getMessage());
        }

        for (Person person : getPersonList()) {
            if (person.getId() == id) {
                return person;
            }
        }
        System.out.println("Person not found ");
        return null;
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> byName = new ArrayList<>();
        String sql = "SELECT * FROM person p WHERE p.first_name LIKE '%" + name + "%';";
        System.out.println(sql);
        try (
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                byName.add(new Person(rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name")));
            }

        } catch (SQLException e) {
            System.err.println("Error connecting " + e.getMessage());
            return Collections.emptyList();
        }

        return byName;
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE person SET first_name = ? , last_name = ? WHERE person_id = ?";

        System.out.println(sql);
        try (
                PreparedStatement pt = connection.prepareStatement(sql);
        ) {
            pt.setString(1, person.getFirstName());
            pt.setString(2, person.getLastName());
            pt.setInt(3, person.getId());

            int rowInserted = pt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Updated successfully.");
                return person;
            }


        } catch (SQLException e) {
            System.err.println("Connecting to DB Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            int rowDeleted = ps.executeUpdate();
            if (rowDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting the person " + e.getMessage());
        }
        return false;
    }
}
