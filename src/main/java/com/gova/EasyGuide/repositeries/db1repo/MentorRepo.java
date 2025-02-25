package com.gova.EasyGuide.repositeries.db1repo;

import com.gova.EasyGuide.entities.bd1.Mentors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MentorRepo extends JpaRepository<Mentors,Long> {


    Optional<Mentors> findByUserEmail(String email);

    Optional<Mentors> findByUserId(Long id);

    @Query(nativeQuery = true,
            value = "SELECT * from mentors_table where (:profession is null or profession = :profession) and (:rating is null or rating = :rating) and (:experience is null or experience = :experience) and (:company is null or comapny = :company) ")
    Optional<Mentors> getmentorlistforUsers(@Param("profession") String profession, @Param("rating") Integer rating,
                                        @Param("experience") Integer experience, @Param("company") String company, Pageable paga);
}
