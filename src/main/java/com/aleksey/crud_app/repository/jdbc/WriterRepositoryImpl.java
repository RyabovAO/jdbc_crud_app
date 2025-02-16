package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.mapping.WriterMapping;
import com.aleksey.crud_app.model.Status;
import com.aleksey.crud_app.model.Writer;
import com.aleksey.crud_app.repository.WriterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private final String INSERT = "INSERT INTO writer VALUES(?, ?, ?, ?)";
    private final String UPDATE = "UPDATE writer SET fistName = ?, lastName = ? WHERE id = ?";
    private final String SELECT_ALL = "";
    private final String SELECT_BY_ID = """
        SELECT * FROM writer
        JOIN post
        ON writer.id = post.writer_id
        JOIN label
        ON post.id = label.post_id
        WHERE writer.id = ? AND label.status = 'ACTIVE'
        """;
    private final String DELETE = "UPDATE writer SET status = 'DELETED' WHERE id = ?";
    private final String COUNT = "SELECT COUNT(id) FROM writer";

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
    public Writer getById(Integer writerId) {
        Writer selectWriter;
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            preparedStatement.setInt(1, writerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            selectWriter = WriterMapping.writerMapping(resultSet).getFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectWriter;

    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    @Override
    public Writer create(Writer writer) {
        int id = getIdCount() + 1;
        writer.setId(id);
        writer.setStatus(Status.ACTIVE);
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            preparedStatement.setInt(1, writer.getId());
            preparedStatement.setString(2, writer.getFistName());
            preparedStatement.setString(3, writer.getLastName());
            preparedStatement.setString(4, writer.getStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, writer.getFistName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setInt(3, writer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

    @Override
    public void deleteById(Integer writerId) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setInt(1, writerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
