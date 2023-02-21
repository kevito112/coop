package com.kev.coop.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This class should not exist because matches shouldn't be created through API.
@RestController
@RequestMapping()
public class MatchesController {
    private final MatchesService matchesService;

    @Autowired
    public MatchesController(MatchesService matchesService){
        this.matchesService = matchesService;
    }

    @GetMapping("matches")
    public List<Matches> getMatches(){
        return matchesService.getMatches();
    }

    @GetMapping("match/{matchUserId1}/{matchUserId2}")
    public Matches getMatch(@PathVariable Long matchUserId1, @PathVariable Long matchUserId2){
        return matchesService.getMatch(matchUserId1, matchUserId2);
    }

    @PostMapping("match/{matchUserId1}")
    public Matches createMatch(@PathVariable Long matchUserId1, @RequestParam Long matchUserId2){
        return matchesService.createMatch(matchUserId1, matchUserId2);
    }
}
