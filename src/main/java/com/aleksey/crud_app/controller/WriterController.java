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

    public Writer createWriter (String fistName, String lastName) {
        Writer writer = new Writer();
        writer.setFistName(fistName);
        writer.setLastName(lastName);
        return writerRepository.create(writer);
    }

    public Writer updateWriter(int id, String fistName, String lastName) {
        Writer writer = getWriterById(id);
        writer.setFistName(fistName);
        writer.setLastName(lastName);
        return writerRepository.update(writer);
    }

    public Writer getWriterById(int id) {
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }

    public void deleteWriterById(int id) {
        writerRepository.deleteById(id);
    }
}
