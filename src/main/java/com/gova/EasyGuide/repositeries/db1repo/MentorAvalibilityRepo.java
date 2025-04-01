package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.Enums.Weekday;
import com.gova.EasyGuide.entities.bd1.MentorAvalibility;
import com.gova.EasyGuide.entities.bd1.Mentors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface MentorAvalibilityRepo extends JpaRepository<MentorAvalibility,Long> {

    boolean existsByMentorAndWeekdayAndStartTimeAndEndTime(Mentors mentor, Weekday weekday, LocalTime startTime, LocalTime endTime);

}
