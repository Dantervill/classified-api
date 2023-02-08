package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.advertisement.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code CommentRepository} class the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds comment by id.
     * @param id the id. Must not be null.
     * @return the optional comment
     */
    @Query("select new Comment (c.postedBy, c.postedAt, c.header, c.body) from Comment c where c.id = ?1 ")
    Optional<Comment> findCommentById(Long id);
}
