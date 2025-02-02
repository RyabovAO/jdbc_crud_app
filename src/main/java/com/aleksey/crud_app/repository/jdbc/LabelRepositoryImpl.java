package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {
    private final String INSERT = "INSERT INTO label(name, post_id) VALUES(?, ?);";
    private final String SELECT_ALL = "SELECT * FROM label;";
    private final String COUNT = "SELECT COUNT(id) FROM label;";

    private int getIdCount() {
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public Label getById(Integer integer) {
        return null;

    }

    @Override
    public List<Label> getAll() throws SQLException {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int post_id = resultSet.getInt("post_id");
                Label lb = new Label(id, name, post_id);
                labels.add(lb);
            }
        }
        return labels;
    }

    @Override
    public Label create(Label label) throws SQLException {
        List<Label> labels = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            preparedStatement.setString(getIdCount(), label.getName());
            preparedStatement.setInt(getIdCount(), label.getPost_id());
            preparedStatement.executeUpdate();
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
