package com.kev.coop.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Long> {
    @Query("select m from Matches m")
    List<Matches> findAllMatchesQuery();

    @Query("select m from Matches m where m.matchUser1.id = ?1 and m.matchUser2.id =?2 or m.matchUser1.id = ?2 and m.matchUser2.id =?1")
    Optional<Matches> findMatchByBothIdsQuery(Long matchUser1Id,Long matchUser2Id);

    boolean existsMatchByMatchUser1IdAndMatchUser2Id(Long matchUser1Id,Long matchUser2Id);
}
