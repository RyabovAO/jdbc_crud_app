package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.model.Status;
import com.aleksey.crud_app.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    private final String INSERT = "INSERT INTO label VALUES(?, ?, ?, ?)";
    private final String UPDATE = "UPDATE label SET name = ?, post_id = ? WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM label";
    private final String SELECT_BY_ID = "SELECT * FROM label WHERE id = ?";
    private final String DELETE = "UPDATE label SET status = 'DELETED' WHERE id = ?";
    private final String COUNT = "SELECT COUNT(id) FROM label";

    private int getIdCount() {
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

        @Override
    public Label getById(Integer labelId) {
        Label selectLabel = new Label();
            try (Connection connection = MySqlConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
            ) {
                preparedStatement.setInt(1, labelId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    selectLabel.setId(resultSet.getInt("id"));
                    selectLabel.setName(resultSet.getString("name"));
                    selectLabel.setPost_id(resultSet.getInt("post_id"));
                    selectLabel.setStatus(Status.valueOf(resultSet.getString("status")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return selectLabel;

    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int post_id = resultSet.getInt("post_id");
                String status = resultSet.getString("status");
                Label lb = new Label(id, name, post_id, Status.valueOf(status));
                labels.add(lb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return labels;
    }

    @Override
    public Label create(Label label) {

        int id = getIdCount() + 1;
        label.setId(id);
        label.setStatus(Status.ACTIVE);
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            preparedStatement.setInt(1, label.getId());
            preparedStatement.setString(2, label.getName());
            preparedStatement.setInt(3, label.getPost_id());
            preparedStatement.setString(4, label.getStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setInt(2, label.getPost_id());
            preparedStatement.setInt(3, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return label;
    }

    @Override
    public void deleteById(Integer integer) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setInt(1, integer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
