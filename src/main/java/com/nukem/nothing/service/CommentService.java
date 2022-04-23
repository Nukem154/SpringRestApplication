package com.nukem.nothing.service;

import com.nukem.nothing.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
