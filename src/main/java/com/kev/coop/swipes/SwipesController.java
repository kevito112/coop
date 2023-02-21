package com.kev.coop.swipes;

import com.kev.coop.user.User;
import com.kev.coop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class SwipesController {

    private final SwipesService swipesService;

    @Autowired
    public SwipesController(SwipesService swipesService) {
        this.swipesService = swipesService;
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

    @PostMapping("swipe/{swiperId}")
    public Swipes createSwipe(@RequestBody Swipes swipe, @PathVariable Long swiperId, @RequestParam Long swipeeId) {
        return swipesService.createSwipe(swipe, swiperId, swipeeId);
    }

    @DeleteMapping("swipe/{swipeId}")
    public void deleteSwipe(@PathVariable Long swipeId){
        swipesService.deleteSwipeBySwipeId(swipeId);

    }
}
