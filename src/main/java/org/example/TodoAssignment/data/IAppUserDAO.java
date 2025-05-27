package org.example.TodoAssignment.data;

import org.example.TodoAssignment.models.AppUser;

import java.util.List;

public interface IAppUserDAO {
    AppUser persist(AppUser appUser);

    AppUser findByUsername(String username);

    List<AppUser> findAll();

    void remove(String userName);
}
