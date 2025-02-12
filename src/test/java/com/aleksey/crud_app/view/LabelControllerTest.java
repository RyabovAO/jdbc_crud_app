package com.aleksey.crud_app.view;

import com.aleksey.crud_app.controller.LabelController;
import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.model.Status;
import com.aleksey.crud_app.repository.LabelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LabelControllerTest {
    @InjectMocks
    private LabelController labelController;
    @Mock
    private LabelRepository labelRepository;

    @Test
    void createLabelTestCorrectedData() {
        Label label = new Label();
        label.setName("testName");
        label.setPost_id(2);
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
    void createLabelTestNameIsNull() {
        Label label = new Label();
        label.setName(null);
        label.setPost_id(2);
        try {
            Mockito.when(labelRepository.create(label)).thenReturn(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label labelForTest;
        try {
            labelForTest = labelController.createLabel(null, 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNull(labelForTest);
    }

    @Test
    void createLabelTestPostIdIsZero() {
        Label label = new Label();
        label.setName("testName");
        label.setPost_id(0);
        try {
            Mockito.when(labelRepository.create(label)).thenReturn(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label labelForTest;
        try {
            labelForTest = labelController.createLabel("testName", 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNull(labelForTest);
    }

    @Test
    void updateLabelTest() {
        Label label = new Label();
        label.setId(2);
        label.setName("newName");
        label.setPost_id(2);
        try {
            Mockito.when(labelRepository.update(label)).thenReturn(label);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label labelForTest;
        try {
            labelForTest = labelController.updateLabel(2, "newName", 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(label, labelForTest);
    }

    @Test
    void getLabelTestCorrectedId() {
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
    void getLabelTestWhenIdIsZero() {

        try {
            Mockito.when(labelRepository.getById(0)).thenReturn(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Label testLabel;
        try {
            testLabel = labelController.getLabelById(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNull(testLabel);
    }

    @Test
    void getAllLabelTest() {
        Label label = new Label(1, "raskaz", 1, Status.ACTIVE);
        Label label2 = new Label(2, "raskaz2", 1, Status.ACTIVE);
        List<Label> expectedList = new ArrayList<>(Arrays.asList(label, label2));
        try {
            Mockito.when(labelRepository.getAll()).thenReturn(expectedList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<Label> actualList;
        try {
            actualList = labelController.getAllLabel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void deleteLabelTestCorrectedId() {

        try {
            Mockito.doNothing().when(labelRepository).deleteById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            labelController.deleteLabelById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Mockito.verify(labelRepository).deleteById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
