package com.aleksey.crud_app.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {

    T getById(ID id) throws SQLException;
    List<T> getAll() throws SQLException;
    T create(T t) throws SQLException;
    T update(T t) throws SQLException;
    void deleteById(ID id) throws SQLException;
}
