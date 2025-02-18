package com.gova.EasyGuide.entities.bd1;

import com.gova.EasyGuide.service.db1.Users.UserService;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users_table")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseUser {

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Courses> purchasedCourses = new HashSet<>();


    public User(String userName, String userEmail, String userPassword) {
        super(userName, userEmail, userPassword);
    }

    public void addCourse(Courses course)
    {
        this.purchasedCourses.add(course);
        course.getUsers().add(this);
    }

    public void removeCourse(Courses course)
    {
        this.purchasedCourses.remove(course);
        course.getUsers().remove(this);
    }

}
