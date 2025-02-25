package com.gova.EasyGuide.entities.bd1;


import com.gova.EasyGuide.Enums.MentorServices;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "mentors_table")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mentors extends BaseUser{

    private String workingCompany;

    private String workingRole;

    @Column(name = "user_rating", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer ratting=0;

    private Integer domainExperience;


    @ElementCollection
    @CollectionTable(name = "mentoring_table",joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "service_type")
    @Column(name = "avalibility")
    private Map<MentorServices,Boolean> mentoringService;

    @ManyToMany(mappedBy = "mentors", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Courses> purchasedCourses = new HashSet<>();


    public void addCourse(Courses course)
    {
        this.getPurchasedCourses().add(course);
        course.getMentors().add(this);
    }

    public void removeCourse(Courses course)
    {
        this.purchasedCourses.remove(course);
        course.getMentors().remove(this);
    }

    public Mentors(String userName, String userEmail, String userPassword) {
        super(userName, userEmail, userPassword);
    }



}
