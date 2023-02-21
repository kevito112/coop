package com.kev.coop.profile;

import com.kev.coop.exceptions.ResourceConflictException;
import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.preferences.Preferences;
import com.kev.coop.preferences.PreferencesService;
import com.kev.coop.profilepicdata.ImageUtils;
import com.kev.coop.user.User;
import com.kev.coop.user.UserRepository;
import com.kev.coop.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;

    public Profile getProfile(Long profileId){
        if(!profileRepository.existsById(profileId)){
            throw new ResourceNotFoundException("User with id of " + profileId + " does not exist");
        }
        return profileRepository.findProfileById(profileId).get();
    }

    public byte[] downloadImage(Long profileId){
        boolean exists = profileRepository.existsById(profileId);
        if(!(exists)){
            throw new ResourceNotFoundException("Profile does not exist with id " + profileId);
        }
        Optional<Profile> profileOptional = profileRepository.findProfileById(profileId);
        return ImageUtils.decompressImage(profileOptional.get().getProfilePic());
    }

    public Profile addProfile(Long userId, Profile profile){
        if (profileRepository.existsById(userId)){
            throw new ResourceConflictException("User already has a profile created. Use PUT to edit fields instead");
        }else{
            User user = userService.getUser(userId);
            profile.setUser(user);
            if(profile.getProfilePic() != null){
                profile.setProfilePic(ImageUtils.compressImage(profile.getProfilePic()));
            }
            //Maybe create a default preferences constructor that fills this info in and call it.
            Preferences preferences = new Preferences();
            preferences.setId(userId);
            preferences.setMileRadius(25);

            if(profile.getGender() == Gender.FEMALE){
                preferences.setGender(Gender.MALE);
            }else{
                preferences.setGender(Gender.FEMALE);
            }
            profile.setPreferences(preferences);
            preferences.setProfile(profile);
            return profileRepository.save(profile);
        }
    }
}
