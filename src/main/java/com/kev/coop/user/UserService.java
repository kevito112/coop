package com.kev.coop.user;

import com.kev.coop.exceptions.InvalidInputException;
import com.kev.coop.exceptions.ResourceConflictException;
import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.profilepicdata.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService { //Service layer, defines db operations to return data to Controller

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with id of " + userId + " does not exist");
        }
        return userRepository.findUserById(userId).get();
    }

    public User getUser(String userEmail) {
        if(!userRepository.existsByEmail(userEmail)){
            throw new ResourceNotFoundException("User with email of " + userEmail + " does not exist");
        }
        return userRepository.findUserByEmail(userEmail).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user){
        Optional<User> userOptional =  userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        return userRepository.save(user);
    }

    public boolean existsById(Long userId){
        return userRepository.existsById(userId);
    }

    /**
    @Transactional
    public User updateUser(Long userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with id " + userId + " does not exist"));
        if(name != null){
            if(name.length() <= 0){
                throw new InvalidInputException("Name provided does not meet basic requirements.");
            }
            if(!name.equals(user.getName())){
                user.setName(name);
            }
        }
        if(email != null){
            if(email.length() <= 0){
                throw new InvalidInputException("Email provided does not meet basic requirements.");
            }
            if(!email.equals(user.getEmail())){
                Optional<User> userOptional = userRepository.findUserByEmail(email);
                if(userOptional.isPresent()){
                    throw new ResourceConflictException("Email provided is already in use");
                }
                user.setEmail(email);
            }
        }
        return user;
    }
    **/

    public void deleteUser(Long userId){
        boolean exists =  userRepository.existsById(userId);
        if(!(exists)){
            throw new IllegalStateException("User does not exist with id " + userId);
        }
        userRepository.deleteById(userId);
    }
}
