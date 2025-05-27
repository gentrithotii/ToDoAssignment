package org.example.TodoAssignment.data;

import java.util.List;

public interface IDAO<T> {
    T create(T t);

    List<T> findAll();

    T findById(int id);

    T update(T t);

    boolean deleteById(int id);
}
