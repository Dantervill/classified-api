package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.MessageRequest;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.Role;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.authentication.AuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.MessageService;
import com.senla.internship.classifiedapi.service.low.level.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageManagementServiceTest {
    @Mock private UserService userService;
    @Mock private Authentication authentication;
    @Mock private MessageService messageService;
    @Mock private AuthenticationFacade authenticationFacade;
    private MessageManagementService messageManagementService;
    private MessageRequest messageRequest;
    private Message message;
    private User authUser;
    private User user2Send;


    @BeforeEach
    void setUp() {
        this.messageManagementService =
                new MessageManagementService(userService, messageService, authenticationFacade);

        Role role = new Role(1L, "USER");

        Profile authProfile = new Profile(1L, "John", "+79125554398", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());

        this.authUser = new User(1L, "johndoe@gmail.com", "qwerty123%", role, authProfile);

        Profile profile2Send = new Profile(2L, "Anna", "+79654903241", 5.0, new BigDecimal(150),
                Collections.emptyList(), Collections.emptyList());

        this.user2Send = new User(2L, "annsmith@gmail.com", "qwerty123%", role, profile2Send);

        this.message = new Message(1L, authUser.getProfile(), user2Send.getProfile(), LocalDateTime.now(),
                "Some text", true, false);

        this.messageRequest = new MessageRequest("Some text");
    }

    @Test
    void shouldGetAllMessages() {
        given(authentication.getName()).willReturn(authUser.getUsername());
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(authUser.getEmail())).willReturn(authUser);

        messageManagementService.receiveAllMessages();

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(authUser.getEmail());
    }

    @Test
    void shouldGetMessage() {
        given(authentication.getName()).willReturn(authUser.getUsername());
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(authUser.getEmail())).willReturn(authUser);
        given(messageService.findByIdAndRecipient(1L, authUser.getProfile())).willReturn(message);

        messageManagementService.receiveMessage(message.getId());

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(messageService).findByIdAndRecipient(message.getId(), authUser.getProfile());

    }

    @Test
    void shouldSendMessage() {
        given(authentication.getName()).willReturn(authUser.getUsername());
        given(authenticationFacade.getAuthentication()).willReturn(authentication);
        given(userService.findByEmail(authUser.getEmail())).willReturn(authUser);
        given(userService.findById(user2Send.getId())).willReturn(user2Send);

        messageManagementService.sendMessage(user2Send.getId(), messageRequest);

        verify(authentication).getName();
        verify(authenticationFacade).getAuthentication();
        verify(userService).findByEmail(authUser.getEmail());
        verify(userService).findById(user2Send.getId());
    }
}