package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.bd1.User;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import com.gova.EasyGuide.service.db1.Users.UserService;
import com.gova.EasyGuide.service.db1.Users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AllbasicController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> userRegistartion(@RequestBody UserRegistartionDto dto)
    {
        userService.regiseterUser(dto);
        return  new ResponseEntity<>("user registered sucesfully",HttpStatus.CREATED);
    }


    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> saveUser(@RequestBody User user, @PathVariable  Long id )
    {
        user.setUserId(id);
         userService.updatingUsersDeatails(user);
         return  new ResponseEntity<>("user data updated sucesfully", HttpStatus.OK);
    }


}
