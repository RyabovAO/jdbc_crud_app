package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.model.*;
import com.aleksey.crud_app.repository.WriterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private final String INSERT = "INSERT INTO writer VALUES(?, ?, ?, ?)";
    private final String UPDATE = "UPDATE writer SET fistName = ?, lastName = ? WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM writer";
    private final String SELECT_ALL_POST = "SELECT * FROM post WHERE writer_id = ? AND post_status = 'ACTIVE'";
    private final String SELECT_BY_ID = "SELECT * FROM writer WHERE id = ?";
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

    private List<Post> getPostListById(int writerId) throws SQLException {
        List<Post> postList = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POST)
        ) {
            preparedStatement.setInt(1, writerId);
            ResultSet resultSetPost = preparedStatement.executeQuery();
            while (resultSetPost.next()) {
                int idPost = resultSetPost.getInt("id");
                String content = resultSetPost.getString("content");
                String created = resultSetPost.getString("created");
                String updated = resultSetPost.getString("updated");
                List<Label> labelList = new PostRepositoryImpl().getLabelListById(idPost);
                int writerID = resultSetPost.getInt("writer_id");
                String postStatus = resultSetPost.getString("post_status");
                Post post = new Post(idPost, content, created, updated, labelList, writerID, PostStatus.valueOf(postStatus));
                postList.add(post);
            }
        }
        return postList;
    }

    @Override
    public Writer getById(Integer writerId) throws SQLException {
        Writer selectWriter = new Writer();
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            preparedStatement.setInt(1, writerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                selectWriter.setId(resultSet.getInt("id"));
                selectWriter.setFistName(resultSet.getString("fistName"));
                selectWriter.setLastName(resultSet.getString("lastName"));
                selectWriter.setPosts(getPostListById(writerId));
                selectWriter.setStatus(Status.valueOf(resultSet.getString("post_status")));
            }
        }
        return selectWriter;

    }

    @Override
    public List<Writer> getAll() throws SQLException {
        List<Writer> writers = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String fistName = resultSet.getString("fistName");
                String lastName = resultSet.getString("lastName");
                List<Post> postList = getPostListById(id);
                String writerStatus = resultSet.getString("status");

                Writer writer = new Writer(id, fistName, lastName, postList, Status.valueOf(writerStatus));
                writers.add(writer);
            }
        }
        return writers;
    }

    @Override
    public Writer create(Writer writer) throws SQLException {
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
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, writer.getFistName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setInt(3, writer.getId());
            preparedStatement.executeUpdate();
        }
        return writer;
    }

    @Override
    public void deleteById(Integer writerId) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setInt(1, writerId);
            preparedStatement.executeUpdate();
        }
    }
}
