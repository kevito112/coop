package com.kev.coop.swipes;

import com.kev.coop.preferences.Preferences;
import com.kev.coop.security.JwtService;
import com.kev.coop.user.User;
import com.kev.coop.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class SwipesController {

    private final SwipesService swipesService;
    private final JwtService jwtService;

    @Autowired
    public SwipesController(SwipesService swipesService, JwtService jwtService) {
        this.swipesService = swipesService;
        this.jwtService = jwtService;
    }

    @GetMapping("swipe/{swipeId}")
    public Swipes getSwipeBySwipeId(@PathVariable Long swipeId) {
        return swipesService.getSwipeBySwipeId(swipeId);
    }

    @GetMapping("swipes/{userId}")
    public List<Swipes> getSwipesByUserId(@PathVariable Long userId) {
        return swipesService.getSwipesByUserId(userId);
    }

    @GetMapping("swipes")
    public List<Swipes> getAllSwipes(){
        return swipesService.getSwipes();
    }

    @PostMapping("swipe/{swipeeId}")
    public Swipes createSwipe(@RequestHeader("Authorization") String token, @RequestBody Swipes swipe, @PathVariable Long swipeeId) {
        Long swiperId= Long.valueOf(jwtService.extractUsername(token.substring(7)));
        return swipesService.createSwipe(swipe, swiperId, swipeeId);
    }

    @DeleteMapping("swipe/{swipeId}")
    public void deleteSwipe(@PathVariable Long swipeId){
        swipesService.deleteSwipeBySwipeId(swipeId);

    }
}
