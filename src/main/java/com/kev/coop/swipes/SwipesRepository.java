package com.kev.coop.swipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SwipesRepository extends JpaRepository<Swipes,Long> {
    Optional<List<Swipes>> findBySwiperUserId(Long swiperId);
    Optional<List<Swipes>> findBySwipeeUserId(Long swiperId);
    Optional<Swipes> findSwipeBySwiperUserIdAndSwipeeUserId(Long swiperId, Long swipeeId);
    boolean existsBySwiperUserIdAndSwipeeUserId(Long swiperId, Long swipeeId);
}
