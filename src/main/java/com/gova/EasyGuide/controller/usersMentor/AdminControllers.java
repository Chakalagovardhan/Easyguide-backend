package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.bd1.CourseDto;
import com.gova.EasyGuide.entities.bd1.Courses;
import com.gova.EasyGuide.entities.bd1.MentorAvalibility;
import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.service.db1.Users.CourseServiceImpl;
import com.gova.EasyGuide.service.db1.Users.MenotrServiceImpl;
import com.gova.EasyGuide.service.db1.Users.MentorService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminControllers {


    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private MenotrServiceImpl mentorService;

    @PostMapping("/add-course")
    public ResponseEntity<Courses> addnewCourse(@RequestBody Courses course)
    {
        courseService.addNewCourse(course);
        return  new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove-course")
    public ResponseEntity<Courses> removeCourse(@RequestBody Courses course)
    {
        courseService.deleteCourse(course.getCourseCode());
        return  new ResponseEntity<>(course,HttpStatus.OK);
    }

    @GetMapping("/list-courses")
    public ResponseEntity<List<CourseDto>> getallCourses()
    {
         courseService.listofCourses();
        return new ResponseEntity<>(courseService.listofCourses(),HttpStatus.OK);
    }

    @PostMapping("/addSlot/{id}")
    public ResponseEntity<String>  addMentorSlots(@RequestBody List<MentorAvalibility> mentorAvalibilityList ,@PathVariable Long id)
    {
        mentorService.addMentorSlots(mentorAvalibilityList,id);
        return  new ResponseEntity<>("New Slots are updated",HttpStatus.OK);
    }

    @GetMapping("/getmentor/{id}")
    public ResponseEntity<Mentors> getMentorById(@PathVariable Long id)
    {

        return  new ResponseEntity<>(mentorService.getMentorWithId(id),HttpStatus.FOUND);
    }




}
