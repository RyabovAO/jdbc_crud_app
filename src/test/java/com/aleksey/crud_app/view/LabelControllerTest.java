package com.aleksey.crud_app.view;

import com.aleksey.crud_app.controller.LabelController;
import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.model.Status;
import com.aleksey.crud_app.repository.LabelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class LabelControllerTest {
    @InjectMocks
    private LabelController labelController;
    @Mock
    private LabelRepository labelRepository;

    @Test
    void createLabelTest() {
        Label label = new Label(4, "testName", 2, Status.ACTIVE);
        try {
            Mockito.when(labelRepository.create(label)).thenReturn(label);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label labelForTest;
        try {
            labelForTest = labelController.createLabel("testName", 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(label, labelForTest);
    }

    @Test
    void updateLabelTest() {

    }

    @Test
    void getLabelTest() {
        Label label = new Label(1, "raskaz", 1, Status.ACTIVE);
        try {
            Mockito.when(labelRepository.getById(1)).thenReturn(label);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Label testLabel;
        try {
            testLabel = labelController.getLabelById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(label, testLabel);
    }

    @Test
    void getAllLabelTest() {

    }

    @Test
    void deleteLabelTest() {

    }
}
