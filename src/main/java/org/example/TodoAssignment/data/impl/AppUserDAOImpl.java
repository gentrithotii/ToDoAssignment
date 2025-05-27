package org.example.TodoAssignment.data.impl;

import org.example.TodoAssignment.data.IAppUserDAO;
import org.example.TodoAssignment.models.AppUser;

import java.util.ArrayList;
import java.util.List;

public class AppUserDAOImpl implements IAppUserDAO {
    private final List<AppUser> appUserList;

    public AppUserDAOImpl() {
        this.appUserList = new ArrayList<>();
    }

    private List<AppUser> getAppUserList() {
        return appUserList;
    }

    @Override
    public AppUser persist(AppUser appUser) {
        if (appUser != null) {
            getAppUserList().add(appUser);
            return appUser;
        }
        throw new IllegalArgumentException("Can't leave empty");
    }

    @Override
    public AppUser findByUsername(String username) {
        for (AppUser user : getAppUserList()) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() {
        return getAppUserList();
    }

    @Override
    public void remove(String username) {
        getAppUserList().removeIf(user -> user.getUserName().equals(username));
    }

}
