package com.senla.internship.classifiedapi.model.account;

import lombok.*;

import javax.persistence.*;

/**
 * The {@code Role} class represents a role table of the database.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
