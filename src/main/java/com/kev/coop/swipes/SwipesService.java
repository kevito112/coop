package com.kev.coop.swipes;

import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.matches.MatchesService;
import com.kev.coop.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwipesService {
    private final SwipesRepository swipesRepository;
    private final UserService userService;
    private final MatchesService matchesService;

    @Autowired
    public SwipesService(SwipesRepository swipesRepository, UserService userService, MatchesService matchesService) {
        this.swipesRepository = swipesRepository;
        this.userService = userService;
        this.matchesService = matchesService;
    }

    public Swipes getSwipeBySwipeId(Long swipeId) {
        return swipesRepository.findById(swipeId).orElse(null);
    }

    public List<Swipes> getSwipesByUserId(Long userId){
        return swipesRepository.findBySwiperUserId(userId).get();
    }

    public List<Swipes> getSwipes(){
        return swipesRepository.findAll();
    }

    @Transactional
    public Swipes createSwipe(Swipes swipe, Long swiperId,Long swipeeId) {
        if(!userService.existsById(swiperId)){
            throw new ResourceNotFoundException("Swiper does not exist with id " + swiperId);
        }else if(!userService.existsById(swipeeId)){
            throw new ResourceNotFoundException("Swipee does not exist with id " + swipeeId);
        }
        if(swipesRepository.existsBySwiperUserIdAndSwipeeUserId(swipeeId, swiperId)){
            Swipes existingSwipe = swipesRepository.findSwipeBySwiperUserIdAndSwipeeUserId(swipeeId, swiperId).get();
            if(existingSwipe.isWouldDate() && swipe.isWouldDate()){
                deleteSwipeBySwipeId(existingSwipe.getSwipeId());
                matchesService.createMatch(swiperId, swipeeId);
            }else{
                deleteSwipeBySwipeId(existingSwipe.getSwipeId());
                throw new IllegalStateException(swipeeId + " has previously stated he wouldn't like to date " + swiperId + " record should be placed in timeout list for N months");
            }
            return null;
        }else{
            swipe.setSwiperUser(userService.getUser(swiperId));
            swipe.setSwipeeUser(userService.getUser(swipeeId));
            return swipesRepository.save(swipe);
        }
    }

    public void deleteSwipeBySwipeId(Long swipeId){
        if(!swipesRepository.existsById(swipeId)){
            throw new ResourceNotFoundException("Swipe does not exist with id " + swipeId);
        }
        swipesRepository.deleteById(swipeId);
    }
}
