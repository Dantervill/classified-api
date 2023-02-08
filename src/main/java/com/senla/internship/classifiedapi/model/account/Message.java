package com.senla.internship.classifiedapi.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The {@code Message} class represents a message table of the database.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @JsonIgnore
    // настройка генерации первичного ключа
    // IDENTITY - Hibernate ищет или создает в DDL определении
    // таблицы специальный столбец первичного ключа с автоматическим
    // приращением, который сам генерирует числовое значение во время выполнения
    // INSERT в базе данных
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Profile recipient;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "sender_id", nullable = false)
    private Profile sender;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "delivered", nullable = false)
    private boolean delivered;

    @Column(name = "read", nullable = false)
    private boolean read;

    /**
     * Constructs a {@code Message} with a sender profile, the time of creation,
     * a text and a delivered boolean.
     * @param sender the sender profile
     * @param sentAt the time of creation
     * @param text the text
     * @param delivered the delivered boolean
     */
    public Message(Profile sender, LocalDateTime sentAt, String text, boolean delivered) {
        this.sender = sender;
        this.sentAt = sentAt;
        this.text = text;
        this.delivered = delivered;
    }

    /**
     * Constructs a {@code Message} with a sender profile, the time of creation,
     * a text, a delivered boolean value, and a read boolean value
     * @param sender the sender profile
     * @param sentAt the time of creation
     * @param text the text
     * @param delivered the delivered boolean
     * @param read the read boolean
     */
    public Message(Profile sender, LocalDateTime sentAt, String text, boolean delivered, boolean read) {
        this.sender = sender;
        this.sentAt = sentAt;
        this.text = text;
        this.delivered = delivered;
        this.read = read;
    }
}
