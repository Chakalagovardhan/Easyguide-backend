package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.User;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.service.db1.Users.MentorService;
import com.gova.EasyGuide.service.db1.Users.UserService;
import com.gova.EasyGuide.service.db1.Users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class AllbasicController {

    @Autowired
    private UserService userService;

    @Autowired
    private MentorService mentorService;


    @PostMapping("/user-signup")
    public ResponseEntity<String> userRegistartion(@RequestBody UserRegistartionDto dto)
    {
        userService.regiseterUser(dto);
        return  new ResponseEntity<>("user registered sucesfully",HttpStatus.CREATED);
    }


    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable  Long id )
    {
        user.setUserId(id);
         userService.updatingUsersDeatails(user);
         return  new ResponseEntity<>("user data updated sucesfully", HttpStatus.OK);
    }

    @PostMapping("/mentor-signup")
    public ResponseEntity<String> mentorRegistration(@RequestBody UserRegistartionDto dto)
    {
        mentorService.registerMentor(dto);
        return new ResponseEntity<>("Mentor is registered is sucesfully",HttpStatus.CREATED);
    }

    @PutMapping("/update-mentor/{id}")
    public ResponseEntity<String> updateUser(@RequestBody Mentors mentor,@PathVariable Long id)
    {
        mentor.setUserId(id);
        mentorService.updateMentor(mentor);
        return  new ResponseEntity<>("Mentor details updated sucesfully",HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public Optional<Mentors> getMentorList(
            @RequestParam(required = false) String profession,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String company,
            Pageable pageable) {
        return mentorService.getMentorListForUsers(profession, rating, experience, company, pageable);
    }



}
