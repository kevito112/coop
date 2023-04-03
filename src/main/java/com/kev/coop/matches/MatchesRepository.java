package com.kev.coop.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Long> {
    @Query("SELECT m FROM Matches m")
    List<Matches> findAllMatchesQuery();

    @Query("SELECT m FROM Matches m WHERE m.matchUser1.id = ?1 AND m.matchUser2.id =?2 OR m.matchUser1.id = ?2 AND m.matchUser2.id =?1")
    Optional<Matches> findMatchByBothIdsQuery(Long matchUser1Id,Long matchUser2Id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Matches m WHERE (m.matchUser1.id = ?1 AND m.matchUser2.id = ?2) OR (m.matchUser1.id = ?2 AND m.matchUser2.id = ?1)")
    boolean existsMatchByMatchUser1IdAndMatchUser2Id(Long matchUser1Id,Long matchUser2Id);
}
