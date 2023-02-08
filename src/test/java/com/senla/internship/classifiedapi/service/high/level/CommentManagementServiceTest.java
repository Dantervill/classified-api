package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.CommentRequest;
import com.senla.internship.classifiedapi.model.advertisement.Location;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.model.advertisement.*;
import com.senla.internship.classifiedapi.security.authentication.AuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.AdvertisementService;
import com.senla.internship.classifiedapi.service.low.level.CommentService;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentManagementServiceTest {
    private User authUser;
    private Comment comment;
    private User user2Comment;
    private CommentRequest commentRequest;
    private Advertisement advertisement;
    @Mock private UserService userService;
    @Mock private Authentication authentication;
    @Mock private CommentService commentService;
    @Mock private AdvertisementService advertisementService;
    @Mock private AuthenticationFacade authenticationFacade;
    private CommentManagementService commentManagementService;


    @BeforeEach
    void setUp() {
        this.commentManagementService =
                new CommentManagementService(userService, commentService, advertisementService, authenticationFacade);

        this.commentRequest = new CommentRequest("Header", "Body");

        Role role = new Role(1L, "USER");

        Profile authProfile = new Profile(1L, "John", "+79125554398", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());

        this.authUser = new User(1L, "johndoe@gmail.com", "qwerty123%", role, authProfile);

        Profile profile2Comment = new Profile(2L, "Anna", "+79654903241", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());

        this.user2Comment = new User(2L, "annsmith@gmail.com", "qwerty123%", role, profile2Comment);

        Location location = new Location(1L, "Moscow", "Moscovskaya Oblast'");

        this.advertisement = new Advertisement(1L, Type.ELECTRONICS, "Title", Condition.SECOND_HAND, Brand.APPLE,
                "Desc", new BigDecimal(150), true, false, location, user2Comment.getProfile(), Collections.emptyList());

        this.comment = new Comment(null, authUser.getProfile(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                commentRequest.getHeader(), commentRequest.getBody(), advertisement);
    }


    @Test
    void shouldPostComment() {
        given(authentication.getName()).willReturn(authUser.getUsername());
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(authUser.getEmail())).willReturn(authUser);
        given(userService.findById(user2Comment.getId())).willReturn(user2Comment);
        given(advertisementService.findByIdAndProfile(advertisement.getId(), user2Comment.getProfile()))
                .willReturn(advertisement);

        commentManagementService.postComment(user2Comment.getId(), advertisement.getId(), commentRequest);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(authUser.getEmail());
        verify(userService).findById(user2Comment.getId());
        verify(advertisementService).findByIdAndProfile(advertisement.getId(), user2Comment.getProfile());
    }

    @Test
    void shouldSaveCommentInPostCommentMethod() {
        commentService.save(comment);

        ArgumentCaptor<Comment> commentArgumentCaptor =
                ArgumentCaptor.forClass(Comment.class);

        verify(commentService).save(commentArgumentCaptor.capture());

        Comment capturedComment = commentArgumentCaptor.getValue();

        assertThat(capturedComment).isEqualTo(comment);
    }
}