package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.configurations.DetailsPatcher;
import com.gova.EasyGuide.entities.bd1.BaseUser;
import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.User;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import com.gova.EasyGuide.repositeries.db1repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DetailsPatcher patcher;

    @Autowired
    private MentorRepo mentorRepo;


    @Override
    public User regiseterUser(UserRegistartionDto dto) {

        Optional<User> userfind = userRepo.findByUserEmail(dto.getDtoUseremail());

        if(userfind.isPresent())
        {
            throw  new AllExceptions.userAllReadyExist("User allready exist with user mail");

        }
            User user = new User(dto.getDtoUsername(), dto.getDtoUseremail(), dto.getDtoUserPassword());
        user.setRoles(Roles.USER);
            return userRepo.save(user);
    }

    @Override
    public void updatingUsersDeatails(User user) {
        Optional<User> currentUser = userRepo.findByUserId(user.getUserId());

        if(currentUser.isPresent())
        {
            User existingUser= currentUser.get();
            try {

                if(user.getUserName() !=null)
                {
                    existingUser.setUserName(user.getUserName());
                }
                if(user.getUserContactNumber() !=null)
                {
                    existingUser.setUserContactNumber(user.getUserContactNumber());
                }
                if(user.getAbout() !=null)
                {
                    existingUser.setAbout(user.getAbout());
                }
                if(user.getProfession() !=null)
                {
                    existingUser.setProfession(user.getProfession());
                }
                if(user.getUserSocailMedia() !=null)
                {
                    existingUser.setUserSocailMedia(user.getUserSocailMedia());
                }
            userRepo.save(existingUser);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else {
            throw new AllExceptions.userAllReadyExist("user does with the given values");
        }

    }


}


