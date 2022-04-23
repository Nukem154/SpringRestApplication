package com.nukem.nothing.controller;

import com.nukem.nothing.entity.Comment;
import com.nukem.nothing.entity.Post;
import com.nukem.nothing.entity.User;
import com.nukem.nothing.service.CommentService;
import com.nukem.nothing.service.PostService;
import com.nukem.nothing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(postService.getAll());
    }

    @PostMapping("/create-post")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, Principal principal) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/create-post").toUriString());
        User user = userService.findByUsername(principal.getName());
        postService.savePost(post, user);
        return ResponseEntity.created(uri).body(post);
    }

    @DeleteMapping(value = "/posts/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        boolean isUserAlreadyExists = postService.checkIfExistsById(id);
        if (!isUserAlreadyExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        postService.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "posts/edit/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id, @Valid @RequestBody Post post) {
        if (id.equals(post.getId())) {
            postService.editPostById(id, post);
        } else return new ResponseEntity<>("Шось не так", HttpStatus.CONFLICT);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/posts/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody Comment comment, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        comment.setAuthorId(user.getId());
        postService.addCommentToPost(id, comment);

       return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/posts/comment/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
