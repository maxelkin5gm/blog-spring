package com.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min=3, message = "Не меньше 3 знаков")
    @Column(name = "email", unique = true)
    private String email;

    @Size(min=3, message = "Не меньше 3 знаков")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Size(min=3, message = "Не меньше 3 знаков")
    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;
}
