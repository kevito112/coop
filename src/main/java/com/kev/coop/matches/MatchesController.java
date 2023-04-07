package com.kev.coop.matches;

import com.kev.coop.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kev.coop.security.JwtService;
import java.util.List;

//This class should not exist because matches shouldn't be created through API.
@RestController
@RequestMapping()
public class MatchesController {
    private final MatchesService matchesService;
    private final JwtService jwtService;

    @Autowired
    public MatchesController(MatchesService matchesService, JwtService jwtService){
        this.matchesService = matchesService;
        this.jwtService = jwtService;
    }

    @GetMapping("matches")
    public List<Matches> getMatches(){
        return matchesService.getMatches();
    }

    @GetMapping("match/{matchUserId1}/{matchUserId2}")
    public Matches getMatch(@PathVariable Long matchUserId1, @PathVariable Long matchUserId2){
        return matchesService.getMatch(matchUserId1, matchUserId2);
    }

    //Should not be exposed
    @PostMapping("match/{matchUserId2}")
    public Matches createMatch(@RequestHeader("Authorization") String token, @PathVariable Long matchUserId2){
        Long matchUserId1= Long.valueOf(jwtService.extractUsername(token.substring(7)));
        return matchesService.createMatch(matchUserId1, matchUserId2);
    }
}
