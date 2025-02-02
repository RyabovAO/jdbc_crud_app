package com.aleksey.crud_app.repository.jdbc;

import com.aleksey.crud_app.model.Post;
import com.aleksey.crud_app.repository.PostRepository;

import java.sql.SQLException;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public Post getById(Integer integer) {
        return null;
    }

    @Override
    public List<Post> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Post create(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
