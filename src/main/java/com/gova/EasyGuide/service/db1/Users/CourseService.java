package com.gova.EasyGuide.service.db1.Users;

import com.gova.EasyGuide.entities.bd1.CourseDto;
import com.gova.EasyGuide.entities.bd1.Courses;

import java.util.List;

public interface CourseService {

    public void addNewCourse(Courses course);

    public void deleteCourse(String courseCode);

    public List<CourseDto> listofCourses();
}
