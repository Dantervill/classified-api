package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.MessageNotFoundException;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.repository.MessageRepository;
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
class MessageServiceTest {
    @Mock private MessageRepository messageRepository;
    private IMessageService messageService;
    private Message message;

    @BeforeEach
    void setUp() {
        this.messageService = new MessageService(messageRepository);
        this.message = Message.builder()
                .id(1L)
                .text("")
                .read(false)
                .delivered(true)
                .sentAt(LocalDateTime.now())
                .recipient(null)
                .sender(null)
                .build();
    }

    @Test
    void save() {
        messageService.save(message);

        ArgumentCaptor<Message> messageArgumentCaptor =
                ArgumentCaptor.forClass(Message.class);

        verify(messageRepository).save(messageArgumentCaptor.capture());

        Message capturedMessage  = messageArgumentCaptor.getValue();

        assertThat(capturedMessage).isEqualTo(message);
    }

    @Test
    void shouldFindByIdAndRecipientWhenNotNull() {
        given(messageRepository.findByIdAndRecipient(message.getId(), message.getRecipient()))
                .willReturn(Optional.ofNullable(message));

        messageService.findByIdAndRecipient(message.getId(), message.getRecipient());

        verify(messageRepository).findByIdAndRecipient(message.getId(), message.getRecipient());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdAndRecipientIsEqualToNull() {
        String message = String.format("Message with ID %d and Recipient %s not found",
                this.message.getId(), this.message.getRecipient());

        assertThatThrownBy(() -> messageService.findByIdAndRecipient(this.message.getId(), this.message.getRecipient()))
                .isInstanceOf(MessageNotFoundException.class)
                .hasMessage(message);
    }
}