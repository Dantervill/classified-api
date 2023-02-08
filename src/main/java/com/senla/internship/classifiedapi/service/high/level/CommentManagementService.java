package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.CommentRequest;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.model.advertisement.Comment;
import com.senla.internship.classifiedapi.security.authentication.IAuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.IAdvertisementService;
import com.senla.internship.classifiedapi.service.low.level.ICommentService;
import com.senla.internship.classifiedapi.service.low.level.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Solves problems of business logic related to comment-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class CommentManagementService {
    private final IUserService userService;
    private final ICommentService commentService;
    private final IAdvertisementService advertisementService;
    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs a {@code CommentManagementService} with a user service,
     * comment service, advertisement service, and authentication facade.
     * @param userService the user service
     * @param commentService the comment service
     * @param advertisementService the advertisement service
     * @param authenticationFacade the authentication facade
     */
    @Autowired
    public CommentManagementService(IUserService userService,
                                    ICommentService commentService,
                                    IAdvertisementService advertisementService,
                                    IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.commentService = commentService;
        this.advertisementService = advertisementService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Posts a comment
     * @param userId the user id
     * @param advertisementId the advertisement id
     * @param commentRequest the comment data access object
     * @return the posted comment
     */
    public Comment postComment(Long userId, Long advertisementId, CommentRequest commentRequest) {
        log.debug("Posting a comment");
        Profile postedBy = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        Profile profile = userService.findById(userId).getProfile();
        Advertisement advertisement = advertisementService.findByIdAndProfile(advertisementId, profile);
        Comment comment = buildComment(postedBy, commentRequest, advertisement);
        comment = commentService.save(comment);
        log.info("Posted comment has been returned to the Controller Layer");
        return comment;
    }

    private Comment buildComment(Profile postedBy, CommentRequest dto, Advertisement advertisement) {
        return Comment.builder()
                .postedBy(postedBy)
                .postedAt(LocalDateTime.now())
                .header(dto.getHeader())
                .body(dto.getBody())
                .advertisement(advertisement)
                .build();
    }
}
