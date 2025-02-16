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
        Label actulaLabel = new Label();
        actulaLabel.setName("testName");
        actulaLabel.setPost_id(2);

        Mockito.when(labelRepository.create(actulaLabel)).thenReturn(actulaLabel);

        Label expectedLabel;
        expectedLabel = labelController.createLabel("testName", 2);

        Assertions.assertEquals(actulaLabel, expectedLabel);
    }

    @Test
    void createLabelTestNameIsNull() {
        Label actualLabel = new Label();
        actualLabel.setName(null);
        actualLabel.setPost_id(2);

        Mockito.when(labelRepository.create(actualLabel)).thenReturn(null);

        Label expectedLabel;
        expectedLabel = labelController.createLabel(null, 2);

        Assertions.assertNull(expectedLabel);
    }

    @Test
    void createLabelTestPostIdIsZero() {
        Label label = new Label();
        label.setName("testName");
        label.setPost_id(0);

        Mockito.when(labelRepository.create(label)).thenReturn(null);

        Label labelForTest;

        labelForTest = labelController.createLabel("testName", 0);

        Assertions.assertNull(labelForTest);
    }

    @Test
    void updateLabelTest() {
        Label label = new Label();
        label.setId(2);
        label.setName("newName");
        label.setPost_id(2);

        Mockito.when(labelRepository.update(label)).thenReturn(label);

        Label labelForTest;

        labelForTest = labelController.updateLabel(2, "newName", 2);

        Assertions.assertEquals(label, labelForTest);
    }

    @Test
    void getLabelTestCorrectedId() {
        Label label = new Label(1, "raskaz", 1, Status.ACTIVE);

        Mockito.when(labelRepository.getById(1)).thenReturn(label);

        Label testLabel;

        testLabel = labelController.getLabelById(1);

        Assertions.assertEquals(label, testLabel);
    }

    @Test
    void getLabelTestWhenIdIsZero() {

        Mockito.when(labelRepository.getById(0)).thenReturn(null);

        Label testLabel;

        testLabel = labelController.getLabelById(0);

        Assertions.assertNull(testLabel);
    }

    @Test
    void getAllLabelTest() {

        Label label = new Label(1, "raskaz", 1, Status.ACTIVE);
        Label label2 = new Label(2, "raskaz2", 1, Status.ACTIVE);
        List<Label> expectedList = new ArrayList<>(Arrays.asList(label, label2));

        Mockito.when(labelRepository.getAll()).thenReturn(expectedList);

        List<Label> actualList;

        actualList = labelController.getAllLabel();

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void deleteLabelTestCorrectedId() {

        Mockito.doNothing().when(labelRepository).deleteById(1);

        labelController.deleteLabelById(1);

        Mockito.verify(labelRepository).deleteById(1);
    }
}
