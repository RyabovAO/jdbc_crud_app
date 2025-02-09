package com.aleksey.crud_app.controller;

import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.repository.LabelRepository;
import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.repository.jdbc.LabelRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;

    public LabelController() {
        this.labelRepository = new LabelRepositoryImpl();
    }

        public Label createLabel(String name, int postId) throws SQLException {
        if(name == null) {
            return null;
        }
        Label label = new Label();
        label.setName(name);
        label.setPost_id(postId);
        return labelRepository.create(label);
    }

    public Label updateLabel(int id, String newName, int newPostId) throws SQLException {
        Label label = new Label();
        label.setId(id);
        label.setName(newName);
        label.setPost_id(newPostId);
        labelRepository.update(label);
        return label;
    }

    public List<Label> getAllLabel() throws SQLException {
        return labelRepository.getAll();
    }

    public Label getLabelById(int id) throws SQLException {
        return labelRepository.getById(id);
    }

    public void deleteLabelById(int id) throws SQLException {
        labelRepository.deleteById(id);
    }
}