package com.aleksey.crud_app.controller;

import com.aleksey.crud_app.model.Writer;
import com.aleksey.crud_app.repository.WriterRepository;
import com.aleksey.crud_app.repository.jdbc.WriterRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class WriterController {
    private final WriterRepository writerRepository;

    public WriterController() {
        this.writerRepository = new WriterRepositoryImpl();
    }

    public Writer createWriter (String fistName, String lastName) throws SQLException {
        Writer writer = new Writer();
        writer.setFistName(fistName);
        writer.setLastName(lastName);
        return writerRepository.create(writer);
    }

    public Writer updateWriter(int id, String fistName, String lastName) throws SQLException {
        Writer writer = getWriterById(id);
        writer.setFistName(fistName);
        writer.setLastName(lastName);
        return writerRepository.update(writer);
    }

    public Writer getWriterById(int id) throws SQLException {
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters() throws SQLException {
        return writerRepository.getAll();
    }

    public void deleteWriterById(int id) throws SQLException {
        writerRepository.deleteById(id);
    }
}
