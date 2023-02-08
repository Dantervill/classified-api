package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.advertisement.Comment;

/**
 * Core interface which processes comment-specific-data.
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface ICommentService {

    /**
     * Saves a comment.
     * @param comment the comment
     * @return the comment
     */
    Comment save(Comment comment);

    /**
     * Finds a comment by id.
     * @param id the id
     * @return the comment
     */
    Comment findCommentById(Long id);
}
