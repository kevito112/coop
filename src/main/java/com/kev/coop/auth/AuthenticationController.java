package com.kev.coop.auth;

import com.kev.coop.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  //There should only be one unique email in db
  //Adds a user to the database using firstname, latname, email, password
  @PostMapping("register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {
    return ResponseEntity.ok(service.register(user));
  }
  //returns a new JWT token for a user.
  //takes in email and password.. kinda login
  @PostMapping("user_login")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  //turn off default spring sec /logout endpoint
  @PutMapping("log-out")
  public String logout(HttpServletRequest request){
    return service.logout(request.getHeader("Authorization").substring(7));
  }
}
