package com.gova.EasyGuide.entities.bd1;

import jakarta.persistence.*;


@Entity
@Table(name = "course_table")
public class Courses {

    @Id
    private String courseCode;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private BaseUser user;

}
