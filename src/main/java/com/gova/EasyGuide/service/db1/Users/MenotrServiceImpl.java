package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.DTOS.MentorAvailabilityResponseDTO;
import com.gova.EasyGuide.DTOS.MentorAvailabilitySlotDTO;
import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.entities.bd1.MentorAvalibility;
import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorAvalibilityRepo;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenotrServiceImpl implements MentorService {

    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private MentorAvalibilityRepo mentorAvalibilityRepo;





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
    public Page<Mentors> getMentorListForUsers(String profession, Integer rating, Integer experience, String company, Pageable pageable) {
        return mentorRepo.getmentorlistforUsers(profession, rating, experience, company, pageable);
    }

    @Override
    public void addMentorSlots(List<MentorAvalibility> mentorSlots, Long id) {
        Optional<Mentors> mentorsOptional = mentorRepo.findByUserId(id);
        DateTimeFormatter formate = DateTimeFormatter.ofPattern("HH:mm");

        if (mentorsOptional.isPresent()) {
            Mentors mentor = mentorsOptional.get();

            for (MentorAvalibility slot : mentorSlots) {
                LocalTime startTime = LocalTime.parse(slot.getStartTime().toString(), formate);
                LocalTime endTime = LocalTime.parse(slot.getEndTime().toString(),formate);

                slot.setStartTime(startTime);
                slot.setEndTime(endTime);
                slot.setMentor(mentor);

                // Check if the slot already exists before inserting
                boolean exists = mentorAvalibilityRepo.existsByMentorAndWeekdayAndStartTimeAndEndTime(
                        mentor, slot.getWeekday(), slot.getStartTime(), slot.getEndTime()
                );

                if (!exists) {
                    mentorAvalibilityRepo.save(slot);
                }
                else {
                    throw new AllExceptions.userNotFoundExist("User with this ID not found");
                }
            }
        } else {
            throw new AllExceptions.userNotFoundExist("User with this ID not found");
        }
    }

    @Override
    public Mentors getMentorWithId(Long id) {

        Optional<Mentors> optionalMentors =mentorRepo.findByUserId(id);
        if(optionalMentors.isPresent())
        {
            return mentorRepo.getMentorsByUserId(id);
        }else {
            throw new AllExceptions.userNotFoundExist("user not found with the given id");
        }
    }

    @Override
    public MentorAvailabilityResponseDTO getPreviousMentorSlots(Long id) {

        Optional<Mentors> optionalMentors = mentorRepo.findByUserId(id);
        List<MentorAvailabilitySlotDTO> slots = null;
        if (optionalMentors.isPresent()) {
            List<MentorAvalibility> mentorAvalibilityList = mentorAvalibilityRepo.findByMentor_UserId(id);
            slots = mentorAvalibilityList.stream().map(slot -> new MentorAvailabilitySlotDTO(
                            slot.getWeekday().toString(),
                            slot.getStartTime().toString(),
                            slot.getEndTime().toString(),
                            slot.getBookingStatus()
                    )
            ).collect(Collectors.toList());
        }

        return new MentorAvailabilityResponseDTO(id, slots);
    }

    @Override
    public Boolean validateMentor(String mail, String password) {
        Optional<Mentors> optionalMentors = mentorRepo.findByUserEmail(mail);
        if(optionalMentors.isPresent())
        {
            String dbEmail =optionalMentors.get().getUserEmail();
            String dbPassword=optionalMentors.get().getUserPassword();

            if(dbEmail.equals(mail) && dbPassword.equals(password))
            {
                return  true;
            }else {
                throw  new AllExceptions.userNotFoundExist("Internal error");
            }
        }else {
            throw  new AllExceptions.userNotFoundExist("User with this mail is not register pleaase register");
        }
    }


}


