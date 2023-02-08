package com.senla.internship.classifiedapi.model.advertisement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senla.internship.classifiedapi.model.account.Profile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The {@code Comment} class represents a comment table.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@Table(name = "advertisement_comment")
public class Comment {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "posted_by", referencedColumnName = "id", nullable = false)
    private Profile postedBy;

    @Column(name = "pasted_at", nullable = false)
    private LocalDateTime postedAt;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id", nullable = false)
    private Advertisement advertisement;

    /**
     * Constructs a {@code Comment} class with id, posted by profile,
     * posted at time, header, body, and advertisement reference.
     * @param id the ID
     * @param postedBy the posted by profile
     * @param postedAt the posted at time
     * @param header the header
     * @param body the body
     * @param advertisement the advertisement reference
     */
    public Comment(Long id, Profile postedBy, LocalDateTime postedAt, String header, String body, Advertisement advertisement) {
        this.id = id;
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.header = header;
        this.body = body;
        this.advertisement = advertisement;
    }

    /**
     * Constructs a {@code Comment} class with posted by profile,
     * posted at time, header, and body.
     * @param postedBy the posted by profile
     * @param postedAt the posted at time
     * @param header the header
     * @param body the body
     */
    public Comment(Profile postedBy, LocalDateTime postedAt, String header, String body) {
        this.postedBy = postedBy;
        this.postedAt = postedAt;
        this.header = header;
        this.body = body;
    }
}
