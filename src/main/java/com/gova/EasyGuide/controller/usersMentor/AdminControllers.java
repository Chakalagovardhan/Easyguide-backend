package com.gova.EasyGuide.controller.usersMentor;


import com.gova.EasyGuide.entities.bd1.CourseDto;
import com.gova.EasyGuide.entities.bd1.Courses;
import com.gova.EasyGuide.service.db1.Users.CourseServiceImpl;
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




}
