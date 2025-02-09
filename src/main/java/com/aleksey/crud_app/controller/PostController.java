package com.aleksey.crud_app.controller;

import com.aleksey.crud_app.model.Post;
import com.aleksey.crud_app.repository.PostRepository;
import com.aleksey.crud_app.repository.jdbc.PostRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    public PostController() {
        this.postRepository = new PostRepositoryImpl();
    }

    public Post createPost(String content, String created, String updated, int writerId) throws SQLException {
        Post post = new Post();
        post.setContent(content);
        post.setCreated(created);
        post.setUpdated(updated);
        post.setWriterId(writerId);
        return postRepository.create(post);
    }

    public Post updatePost(int id, String content, String created, String updated, int writeId) throws SQLException {
        Post post = getPostById(id);
        post.setContent(content);
        post.setCreated(created);
        post.setUpdated(updated);
        post.setWriterId(writeId);
        return postRepository.update(post);
    }

    public Post getPostById(int id) throws SQLException {
        return postRepository.getById(id);
    }

    public List<Post> getAllPost() throws SQLException {
        return postRepository.getAll();
    }

    public void deletePostById(int id) throws SQLException {
        postRepository.deleteById(id);
    }
}
