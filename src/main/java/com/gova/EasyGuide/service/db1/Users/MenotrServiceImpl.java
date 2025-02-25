package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Optional;

@Service
public class MenotrServiceImpl implements MentorService {

    @Autowired
    private MentorRepo mentorRepo;

    @Override
    public Mentors registerMentor(UserRegistartionDto dto) {
        Optional<Mentors> userfind = mentorRepo.findByUserEmail(dto.getDtoUseremail());

        if(userfind.isPresent())
        {
            throw  new AllExceptions.userAllReadyExist("User allready exist with user mail");

        }
        Mentors mentor = new Mentors(dto.getDtoUsername(), dto.getDtoUseremail(), dto.getDtoUserPassword());
        mentor.setRatting(0);
        mentor.setRoles(Roles.Mentor);
        return mentorRepo.save(mentor);
    }

    @Override
    public Mentors updateMentor(Mentors mentor) {
       Optional<Mentors> mentorfind=mentorRepo.findByUserId(mentor.getUserId());
       if(mentorfind.isPresent())
       {
          Mentors current= mentorfind.get();

          if(mentor.getWorkingCompany() !=null)
          {
              current.setWorkingCompany(mentor.getWorkingCompany());
          }
          if(mentor.getWorkingCompany() !=null)
          {
              current.setWorkingRole(mentor.getWorkingRole());
          }
          if(mentor.getAbout() !=null)
          {
              current.setAbout(mentor.getAbout());
          }
          if(mentor.getMentoringService() !=null)
          {
              if(current.getMentoringService()==null)
              {
                  current.setMentoringService(new HashMap<>());
              }
              current.getMentoringService().putAll(mentor.getMentoringService());
          }

          return mentorRepo.save(current);
       }
       else {
           throw new AllExceptions.userAllReadyExist("User with the give id doesn't exist");
       }
    }

    @Override
    public Optional<Mentors> getMentorListForUsers(String profession, Integer rating, Integer experience, String company, Pageable pageable) {
        return mentorRepo.getmentorlistforUsers(profession, rating, experience, company, pageable);
    }

}


