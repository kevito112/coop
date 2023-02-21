package com.kev.coop.matches;

import com.kev.coop.exceptions.ResourceConflictException;
import com.kev.coop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchesService {
    private final MatchesRepository matchesRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public MatchesService(MatchesRepository matchesRepository, UserRepository userRepository){
        this.matchesRepository = matchesRepository;
        this.userRepository = userRepository;
    }

    public List<Matches> getMatches(){
        return matchesRepository.findAllMatchesQuery();
    }
    public Matches getMatch(Long matchUserId1, Long matchUserId2){
        return matchesRepository.findMatchByBothIdsQuery(matchUserId1,matchUserId2).get();
    }

    public Matches createMatch(Long matchUserId1, Long matchUserId2){
        //add functionality to use the JWT token ID and make sure the matchuserId1 matches it.
        if(matchUserId1 == matchUserId2){
            throw new ResourceConflictException("Cannot match user with themself");
        }
        if(matchesRepository.existsMatchByMatchUser1IdAndMatchUser2Id(matchUserId1, matchUserId2)){
            throw new ResourceConflictException("Match between id " + matchUserId1 + " and id " + matchUserId2 + " already exists");
        }
        if(matchesRepository.existsMatchByMatchUser1IdAndMatchUser2Id(matchUserId2, matchUserId1)){
            throw new ResourceConflictException("Match between id " + matchUserId2 + " and id " + matchUserId1 + " already exists");
        }
        Matches match = new Matches();
        match.setMatchUser1(userRepository.findUserById(matchUserId1).get());
        match.setMatchUser2(userRepository.findUserById(matchUserId2).get());

        return matchesRepository.save(match);
    }
}
