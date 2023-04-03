package com.kev.coop.profile;

import com.kev.coop.exceptions.InvalidInputException;
import com.kev.coop.exceptions.ResourceConflictException;
import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.preferences.Preferences;
import com.kev.coop.preferences.PreferencesRepository;
import com.kev.coop.preferences.PreferencesService;
import com.kev.coop.profilepicdata.ImageUtils;
import com.kev.coop.user.User;
import com.kev.coop.user.UserRepository;
import com.kev.coop.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final PreferencesService preferencesService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserService userService, @Lazy PreferencesService preferencesService){
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.preferencesService = preferencesService;
    }
    public Profile getProfile(Long profileId){
        if(!profileRepository.existsById(profileId)){
            throw new ResourceNotFoundException("User with id of " + profileId + " does not exist");
        }
        return profileRepository.findProfileById(profileId).get();
    }

    public List<Profile> getFilteredList(Long userId){
        Preferences pref = preferencesService.getPreferences(userId);
        Profile prof = profileRepository.findProfileById(userId).get();
        Gender genderPref = pref.getGender();
        int lowerLimitAge = pref.getLowerLimitAge();
        int upperLimitAge = pref.getUpperLimitAge();
        Gender genderProf = prof.getGender();
        LocalDate dob = prof.getDob();
        return profileRepository.findMatchingProfiles(genderPref, lowerLimitAge, upperLimitAge, genderProf, dob);
    }

    public byte[] downloadImage(Long profileId){
        boolean exists = profileRepository.existsById(profileId);
        if(!(exists)){
            throw new ResourceNotFoundException("Profile does not exist with id " + profileId);
        }
        Optional<Profile> profileOptional = profileRepository.findProfileById(profileId);
        return ImageUtils.decompressImage(profileOptional.get().getProfilePic());
    }

/** Add functionality once its pushed to AWS
    public void uploadProfilePic(Long userId,MultipartFile file){
        if(file.isEmpty()){
            throw new InvalidInputException("Image not provided");
        }
        if(!Arrays.asList(ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_JPEG.getMimeType()).contains(file.getContentType())){
            throw new InvalidInputException("Image must be of type PNG/JPEG only");
        }
        Profile profile = profileRepository.findProfileById(userId).orElseThrow(() -> new ResourceNotFoundException("There is no profile with id: " + userId));

    }
**/

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
