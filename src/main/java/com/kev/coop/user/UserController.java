package com.kev.coop.user;

import com.kev.coop.auth.AuthenticationResponse;
import com.kev.coop.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import com.kev.coop.exceptions.CustomizedExceptionHandling;

@RestController
@RequestMapping
public class UserController {  // API / Controller Layer, uses service layer to retrieve data.. exposed to front-end
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping(value = "user/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
    @PutMapping(path = "user/{userId}")
    public User updateUser(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String email){

        return userService.updateUser(userId, name, email);
    }
     **/

    //Admin only
    @DeleteMapping(path = "user/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @DeleteMapping(path = "user")
    public void deleteUser(@RequestHeader("Authorization") String token){
        Long userId= Long.valueOf(jwtService.extractUsername(token.substring(7)));
        deleteUser(userId);
    }


}
