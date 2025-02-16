package com.aleksey.crud_app.view;

import com.aleksey.crud_app.controller.WriterController;
import com.aleksey.crud_app.model.Writer;

import java.sql.SQLException;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner = new Scanner(System.in);

    public WriterView() {
        this.writerController = new WriterController();
    }

    public void getAllWriter() {
        writerController.getAllWriters().forEach(w -> System.out.println(w.toString()));
    }

    public void getWriter() {
        System.out.println("Enter writer id");
        int id = scanner.nextInt();
        Writer writer = writerController.getWriterById(id);
        System.out.println("writer with ID: " + writer.toString());
    }

    public void createWriter() {
        System.out.println("Enter fist name");
        String fistName = scanner.nextLine();
        System.out.println("Enter last name");
        String lastName = scanner.nextLine();

        Writer createdWriter = writerController.createWriter(fistName, lastName);

        System.out.println("Created writer: " + createdWriter);
    }

    public void updateWriter() {
        System.out.println("Enter writer id");
        int id = scanner.nextInt();
        System.out.println("Enter new fist name");
        String newFistName = scanner.nextLine();
        System.out.println("Enter new last name");
        String newLastName = scanner.nextLine();

        Writer updateWriter = writerController.updateWriter(id, newFistName, newLastName);

        System.out.println("writer with id update to: " + updateWriter);
    }

    public void deleteWriter() {
        System.out.println("Enter writer id");
        int id = scanner.nextInt();
        writerController.deleteWriterById(id);
        System.out.println("writer with id: " + id + " delete");
    }
}
