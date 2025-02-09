package com.aleksey.crud_app.view;

import java.sql.SQLException;
import java.util.Scanner;

public class MainView {
    private final LabelView labelView = new LabelView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("Select table:");
        System.out.println("1 - label");
        System.out.println("2 - post");
        System.out.println("3 - writer");
        int num = Integer.parseInt(scanner.nextLine());
        switch (num) {
            case 1:
                labelWork();
                break;
            case 2:
                postWork();
                break;
            case 3:
                writerWork();
                break;
            default:
                System.out.println("Не верное значение");
        }
    }

    private void labelWork() {
        System.out.println("Select action");
        System.out.println("1 - create entry");
        System.out.println("2 - update entry");
        System.out.println("3 - get by id");
        System.out.println("4 - get all entry");
        System.out.println("5 - delete entry by id");
        int num = Integer.parseInt(scanner.nextLine());
        switch (num) {
            case 1:
                try {
                    labelView.createLabel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    labelView.updateLabel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    labelView.getLabel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 4:
                try {
                    labelView.getAllLabel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 5:
                try {
                    labelView.deleteLabel();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Не верное значение");
        }
    }

    private void postWork() {
        System.out.println("Select action");
        System.out.println("1 - create entry");
        System.out.println("2 - update entry");
        System.out.println("3 - get by id");
        System.out.println("4 - get all entry");
        System.out.println("5 - delete entry by id");
        int num = Integer.parseInt(scanner.nextLine());
        switch (num) {
            case 1:
                try {
                    postView.createPost();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    postView.updatePost();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    postView.getPost();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 4:
                try {
                    postView.getAllPost();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 5:
                try {
                    postView.deletePost();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Не верное значение");
        }
    }


    private void writerWork() {
        System.out.println("Select action");
        System.out.println("1 - create entry");
        System.out.println("2 - update entry");
        System.out.println("3 - get by id");
        System.out.println("4 - get all entry");
        System.out.println("5 - delete entry by id");
        int num = Integer.parseInt(scanner.nextLine());
        switch (num) {
            case 1:
                try {
                    writerView.createWriter();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                try {
                    writerView.updateWriter();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                try {
                    writerView.getWriter();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 4:
                try {
                    writerView.getAllWriter();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 5:
                try {
                    writerView.deleteWriter();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Не верное значение");
        }
    }
}

