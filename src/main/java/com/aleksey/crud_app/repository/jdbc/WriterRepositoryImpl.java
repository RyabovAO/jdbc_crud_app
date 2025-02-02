package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.model.Writer;
import com.aleksey.crud_app.repository.WriterRepository;

import java.sql.SQLException;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer getById(Integer integer) {
        return null;
    }

    @Override
    public List<Writer> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Writer create(Writer writer) {
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
