package com.kev.coop.auth;

import com.kev.coop.auth.AuthenticationRequest;
import com.kev.coop.exceptions.ResourceConflictException;
import com.kev.coop.security.JwtService;
import com.kev.coop.user.Role;
import com.kev.coop.user.User;
import com.kev.coop.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RedisTemplate<String, Object> redisTemplate;

  public AuthenticationResponse register(User user) {
    //Extracts info from user and creates user object
    if(repository.existsByEmail(user.getEmail())){
      throw new ResourceConflictException("Email is already taken, user not created");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    repository.save(user);
    //jwtService takes the user and creates a token for it.
    var jwtToken = jwtService.generateToken(user);
    //return the token
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = repository.findUserByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceConflictException("User not found"));
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public String logout(String request){
    String userId = jwtService.extractUsername(request);
    Date currentTime = new Date();
    Duration duration = Duration.ofMinutes(JwtService.getDURATION());
    redisTemplate.opsForValue().set(userId, currentTime.toString(), duration);
    return "Successfully Logged Out";
  }
}
