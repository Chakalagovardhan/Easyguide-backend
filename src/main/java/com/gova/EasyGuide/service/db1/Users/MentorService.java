package com.gova.EasyGuide.service.db1.Users;



import com.gova.EasyGuide.entities.bd1.MentorAvalibility;
import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface MentorService {
    public Mentors registerMentor(UserRegistartionDto dto);

    public Mentors updateMentor(Mentors mentor);

    Page<Mentors> getMentorListForUsers(String profession, Integer rating, Integer experience, String company, Pageable pageable);

    public void addMentorSlots(List<MentorAvalibility> mentorSlots,Long id);

    public Mentors getMentorWithId(Long id);


}



