package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.configurations.DetailsPatcher;
import com.gova.EasyGuide.entities.db1.User;
import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db1.UserLogin;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import com.gova.EasyGuide.repositeries.db1repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DetailsPatcher patcher;

    @Autowired
    private MentorRepo mentorRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    @Override
    public User regiseterUser(UserRegistartionDto dto) throws AllExceptions.userAllReadyExist {

        Optional<User> userfind = userRepo.findByUserEmail(dto.getDtoUseremail());

        if(userfind.isPresent())
        {
            throw  new AllExceptions.userAllReadyExist("User allready exist with user mail");

        }
        //to encrypt the password
        dto.setDtoUserPassword(encoder.encode(dto.getDtoUserPassword()));
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
            throw new AllExceptions.userNotFound("user not found with");
        }

    }



    //verifyting the username and the password at the time of thelogin
    @Override
    public HashMap<String,String> validateUser(UserLogin userLogin) {
        System.out.println("Authenticating user: " + userLogin.getUserName());

        Optional<User> user = userRepo.findByUserName(userLogin.getUserName());
        if(user.isPresent())
        {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLogin.getUserName(),
                                userLogin.getUserPassword()
                        )
                );

                HashMap<String,String> keys = new HashMap<>();
                keys.put("JWT_TOKEN", jwtService.generateToken(userLogin.getUserName()));
                keys.put("REFRESH_TOKEN", jwtService.getRefreshToken(
                        userLogin.getUserName(),
                        userLogin.getUserPassword()
                ));
                return keys;

            } catch (AuthenticationException e) {
                // Return empty map when authentication fails
                throw new AllExceptions.invalidCredentails("Credentails are not matched");
            }
        }
        throw  new AllExceptions.userNotFound("User with this username is not register pleaase register");
    }

    public User getUserDetails(String userName)
    {
        Optional<User> user = userRepo.findByUserName(userName);
        if(user.isPresent())
        {
            return user.get();
        }
        else{
            throw new RuntimeException("User wiht this name is not found");
        }
    }


}


