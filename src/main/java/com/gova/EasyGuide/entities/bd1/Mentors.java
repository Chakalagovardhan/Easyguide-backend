package com.gova.EasyGuide.entities.bd1;


import com.gova.EasyGuide.Enums.MentorServices;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

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

    private double userRating;

    private Integer domainExperience;


    @ElementCollection
    @CollectionTable(name = "mentoring_table",joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "service_type")
    @Column(name = "avalibility")
    private Map<MentorServices,Boolean> mentoringService;






}
