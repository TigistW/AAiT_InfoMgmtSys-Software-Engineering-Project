package com.aait.aaitims.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "course_id", referencedColumnName = "id")
    // private Courses courses;

    @Column(nullable = false, unique = true, length = 45)
    private String email;
    @Column(nullable = false, length = 45)
    private String firstname;
    @Column(nullable = false, length = 20)
    private String lastname;
    @Column(nullable = false, unique = true, length = 64)
    private String password;
    @Column
    private String role;

}