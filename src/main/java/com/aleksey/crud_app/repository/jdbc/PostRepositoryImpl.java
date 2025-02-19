package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.mapping.PostMapping;
import com.aleksey.crud_app.model.Post;
import com.aleksey.crud_app.model.PostStatus;
import com.aleksey.crud_app.repository.PostRepository;

import java.sql.*;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final String INSERT = "INSERT INTO post VALUES(?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE post SET content = ?, created = ?, updated = ?, writer_Id = ? WHERE id = ?";
    private final String SELECT_ALL = """
            SELECT * FROM post
                JOIN post_label
                ON post.id = post_label.post_id
                JOIN label
                ON post_label.label_id = label.id
            """;
    private final String SELECT_BY_ID = """
                SELECT * FROM post
                JOIN post_label
                ON post.id = post_label.post_id
                JOIN label
                ON post_label.label_id = label.id
                WHERE post.id = ?;
            """;
    private final String DELETE = "UPDATE post SET post_status = 'DELETED' WHERE id = ?";
    private final String COUNT = "SELECT COUNT(id) FROM post";

    private int getIdCount() {
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public Post getById(Integer postId) {
        Post selectPost;
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            selectPost = PostMapping.postMapping(resultSet).getFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectPost;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = null;
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            posts = PostMapping.postMapping(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public Post create(Post post) {
        int id = getIdCount() + 1;
        post.setId(id);
        post.setPostStatus(PostStatus.ACTIVE);
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            preparedStatement.setInt(1, post.getId());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getCreated());
            preparedStatement.setString(4, post.getUpdated());
            preparedStatement.setInt(5, post.getWriterId());
            preparedStatement.setString(6, post.getPostStatus().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getCreated());
            preparedStatement.setString(3, post.getUpdated());
            preparedStatement.setInt(4, post.getWriterId());
            preparedStatement.setInt(5, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public void deleteById(Integer postId) {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
