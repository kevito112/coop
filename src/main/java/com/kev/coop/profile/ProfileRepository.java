package com.kev.coop.profile;

import com.kev.coop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileById(Long id);

    @Query("SELECT p FROM Profile p " +
            "JOIN p.preferences pr " +
            "WHERE p.gender = :genderPrefSearcher " +
            "AND YEAR(CURRENT_DATE) - YEAR(p.dob) BETWEEN :lowerLimitAgePrefSearcher AND :upperLimitAgePrefSearcher " +
            "AND pr.gender = :genderProfSearcher " +
            "AND YEAR(CURRENT_DATE) - YEAR(:ageProfSearcher) BETWEEN pr.lowerLimitAge AND pr.upperLimitAge")
    List<Profile> findMatchingProfiles(@Param("genderPrefSearcher") Gender genderPrefSearcher,
                                       @Param("lowerLimitAgePrefSearcher") int lowerLimitAgePrefSearcher,
                                       @Param("upperLimitAgePrefSearcher") int upperLimitAgePrefSearcher,
                                       @Param("genderProfSearcher") Gender genderProfSearcher,
                                       @Param("ageProfSearcher") LocalDate ageProfSearcher);
}
