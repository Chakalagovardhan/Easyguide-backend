package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.entities.bd1.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends JpaRepository<Courses,String> {
}
