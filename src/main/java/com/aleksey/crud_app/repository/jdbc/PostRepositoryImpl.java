package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.DBUtils.MySqlConnection;
import com.aleksey.crud_app.model.Label;
import com.aleksey.crud_app.model.Post;
import com.aleksey.crud_app.model.PostStatus;
import com.aleksey.crud_app.model.Status;
import com.aleksey.crud_app.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final String INSERT = "INSERT INTO post VALUES(?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE post SET content = ?, created = ?, updated = ?, writer_Id = ? WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM post";
    private final String SELECT_ALL_LABELS = "SELECT * FROM label WHERE post_id = ? AND status = 'ACTIVE'";
    private final String SELECT_BY_ID = "SELECT * FROM post WHERE id = ?";
    private final String DELETE = "UPDATE post SET post_status = 'DELETED' WHERE id = ?";
    private final String COUNT = "SELECT COUNT(id) FROM post";

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

    public List<Label> getLabelListById(int postId) throws SQLException {
        List<Label> labelList = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LABELS)
        ) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSetLabel = preparedStatement.executeQuery();
            while (resultSetLabel.next()) {
                int idLabel = resultSetLabel.getInt("id");
                String nameLabel = resultSetLabel.getString("name");
                int post_id = resultSetLabel.getInt("post_id");
                String statusLabel = resultSetLabel.getString("status");
                Label lb = new Label(idLabel, nameLabel, post_id, Status.valueOf(statusLabel));
                labelList.add(lb);
            }
        }
        return labelList;
    }

    @Override
    public Post getById(Integer postId) throws SQLException {
        Post selectPost = new Post();
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                selectPost.setId(resultSet.getInt("id"));
                selectPost.setContent(resultSet.getString("content"));
                selectPost.setCreated(resultSet.getString("created"));
                selectPost.setUpdated(resultSet.getString("updated"));
                selectPost.setWriterId(resultSet.getInt("writer_id"));
                selectPost.setLabels(getLabelListById(postId));
                selectPost.setPostStatus(PostStatus.valueOf(resultSet.getString("post_status")));
            }
        }
        return selectPost;

    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                String created = resultSet.getString("created");
                String updated = resultSet.getString("updated");
                List<Label> labelList = getLabelListById(id);
                int writerId = resultSet.getInt("writer_id");
                String postStatus = resultSet.getString("post_status");

                Post post = new Post(id, content, created, updated,  labelList, writerId, PostStatus.valueOf(postStatus));
                posts.add(post);
            }
        }
        return posts;
    }

    @Override
    public Post create(Post post) throws SQLException {
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
        }
        return post;
    }

    @Override
    public Post update(Post post) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getCreated());
            preparedStatement.setString(3, post.getUpdated());
            preparedStatement.setInt(4, post.getWriterId());
            preparedStatement.setInt(5, post.getId());
            preparedStatement.executeUpdate();
        }
        return post;
    }

    @Override
    public void deleteById(Integer postId) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)
        ) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        }
    }
}
