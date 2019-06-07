package com.place4code.reddit.service;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.repo.CommentRepo;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment) {
        commentRepo.save(comment);
    }
}
