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

        public Label createLabel(String name, int postId) {
        if(name == null || postId == 0) {
            return null;
        }
        Label label = new Label();
        label.setName(name);
        return labelRepository.create(label);
    }

    public Label updateLabel(int id, String newName, int newPostId) {
        if(id == 0 || newName == null || newPostId == 0) {
            return null;
        }
        Label label = new Label();
        label.setId(id);
        label.setName(newName);
        labelRepository.update(label);
        return label;
    }

    public List<Label> getAllLabel() {
        return labelRepository.getAll();
    }

    public Label getLabelById(int id) {
        if(id == 0) {
            System.out.println("Uncorrected id");
            return null;
        }
        return labelRepository.getById(id);
    }

    public void deleteLabelById(int id) {
        if(id == 0) {
            System.out.println("Uncorrected id");
        } else labelRepository.deleteById(id);

    }
}