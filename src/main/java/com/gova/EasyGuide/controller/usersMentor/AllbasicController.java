package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.User;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.service.db1.Users.MentorService;
import com.gova.EasyGuide.service.db1.Users.UserService;
import com.gova.EasyGuide.service.db1.Users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> getMentorList(
            @RequestParam(required = false) String profession,
            @RequestParam(required = false ,defaultValue = "0") Integer rating,
            @RequestParam(required = false ,defaultValue = "0") Integer experience,
            @RequestParam(required = false) String company,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false,defaultValue = "10")Integer pageSize,
            @RequestParam(required = false,defaultValue ="userId") String sortBy,
            @RequestParam(required = false,defaultValue = "true") Boolean ascending
            ) {
//        Sort sort=ascending?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        System.out.println("Received parameters:");
        System.out.println("profession: " + profession);
        System.out.println("rating: " + rating);
        System.out.println("experience: " + experience);
        System.out.println("company: " + company);
        System.out.println("pagenumber: " + pageNumber);
        System.out.println("ascending"+ascending);
        Pageable pagable= PageRequest.of(pageNumber,pageSize);
        Page<Mentors> data = mentorService.getMentorListForUsers(profession, rating, experience, company, pagable);

        if(data.hasContent())
        {
            List<Mentors> sortedMentors = data.getContent().stream().sorted(
                ascending? Comparator.comparing(Mentors::getUserName):Comparator.comparing(Mentors::getUserName).reversed()
            ).collect(Collectors.toList());
            Map<String, Object> response = new HashMap<>();
            response.put("content", data.getContent());
            response.put("totalPages", data.getTotalPages());
            response.put("totalElements", data.getTotalElements());
            response.put("currentPage", data.getNumber());
            response.put("size", data.getSize());
            response.put("length",data.getNumberOfElements());
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }else {
            return  new ResponseEntity<>("No content is matched with the follwing requirments",HttpStatus.NO_CONTENT);
        }

    }



}
