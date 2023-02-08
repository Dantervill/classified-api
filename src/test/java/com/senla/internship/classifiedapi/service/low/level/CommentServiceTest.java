package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.CommentNotFoundException;
import com.senla.internship.classifiedapi.model.advertisement.Comment;
import com.senla.internship.classifiedapi.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock private CommentRepository commentRepository;
    private ICommentService commentService;
    private Comment comment;

    @BeforeEach
    void setUp() {
        this.commentService = new CommentService(commentRepository);
        this.comment = Comment.builder()
                .id(1L)
                .header("")
                .body("")
                .postedBy(null)
                .postedAt(LocalDateTime.now())
                .advertisement(null)
                .build();
    }

    @Test
    void shouldSave() {
        commentService.save(comment);

        ArgumentCaptor<Comment> commentArgumentCaptor =
                ArgumentCaptor.forClass(Comment.class);

        verify(commentRepository).save(commentArgumentCaptor.capture());

        Comment capturedComment = commentArgumentCaptor.getValue();

        assertThat(capturedComment).isEqualTo(comment);
    }

    @Test
    void shouldFindCommentByIdWhenNotNull() {
        given(commentRepository.findCommentById(comment.getId()))
                .willReturn(Optional.ofNullable(comment));

        commentService.findCommentById(comment.getId());

        verify(commentRepository).findCommentById(comment.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindCommentByIdIsEqualToNull() {
        String message = String.format("Comment with id %d not found", comment.getId());

        assertThatThrownBy(() -> commentService.findCommentById(comment.getId()))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage(message);
    }
}