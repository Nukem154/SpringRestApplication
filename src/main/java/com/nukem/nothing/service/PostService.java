package com.nukem.nothing.service;

import com.nukem.nothing.entity.Comment;
import com.nukem.nothing.entity.Post;
import com.nukem.nothing.entity.User;
import com.nukem.nothing.exception.exceptions.PostNotFoundException;
import com.nukem.nothing.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void savePost(Post post, User user) {
        post.setUserId(user.getId());
        postRepository.save(post);
    }

    public boolean checkIfExistsById(Long id) {
        return postRepository.existsById(id);
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void editPostById(Long id, Post post) {
        Post postFromDb = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        postFromDb.setTitle(post.getTitle());
        postFromDb.setDescription(post.getDescription());
        postRepository.save(postFromDb);
    }

    public void addCommentToPost(Long postId, Comment comment) {
        Post postFromDb = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        postFromDb.addComment(comment);
        postRepository.save(postFromDb);
    }
}
