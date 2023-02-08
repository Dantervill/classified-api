package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.CommentNotFoundException;
import com.senla.internship.classifiedapi.model.advertisement.Comment;
import com.senla.internship.classifiedapi.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the {@link ICommentService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save (Comment comment) {
        log.info("Comment has been saved");
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long id) throws CommentNotFoundException {
        String message = String.format("Comment with id %d not found", id);
        log.info("Comment has been found by id");
        return commentRepository.findCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException(message));
    }
}
