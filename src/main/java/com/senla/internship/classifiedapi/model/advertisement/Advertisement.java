package com.senla.internship.classifiedapi.model.advertisement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.senla.internship.classifiedapi.model.account.Profile;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * The {@code Advertisement} class represents an advertisement table.
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
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "vip", nullable = false)
    private boolean vip;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id",
    nullable = false)
    private Location location;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "profile_id", referencedColumnName = "id",
    nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Comment> comments;

    /**
     * Constructs an {@code Advertisement} with a vip boolean,
     * an active boolean, type of advertisement, title of
     * advertisement, a condition boolean value, brand of advertisement,
     * description of advertisement, price of advertisement, location of
     * advertisement, and a profile of an advertisement.
     * @param vip the vip boolean
     * @param active the active boolean
     * @param type the type
     * @param title the title
     * @param condition the condiiton
     * @param brand the brand
     * @param description the descryption
     * @param price the price
     * @param location the location
     * @param profile the profile
     */
    public Advertisement(boolean vip, boolean active, Type type,
                         String title, Condition condition, Brand brand,
                         String description, BigDecimal price,
                         Location location, Profile profile) {
        this.vip = vip;
        this.active = active;
        this.type = type;
        this.title = title;
        this.condition = condition;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.location = location;
        this.profile = profile;
    }
}
