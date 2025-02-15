package com.gova.EasyGuide.entities.bd1;


import com.gova.EasyGuide.Enums.Roles;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UserId;

    private String UserName;

    private String UserEmail;

    private Long userContactNumber;

    private String Profession;

    private String userImageUrl;

    private String about;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @ElementCollection
    @CollectionTable(name = "user_social_medias" ,joinColumns = @JoinColumn(name="user_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "platform_url")
    private Map<String,String> userSocailMedia = new HashMap<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Courses> purchasedCourses ;





}
