package com.kev.coop.preferences;

import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.profile.Profile;
import com.kev.coop.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferencesService {
    private final ProfileService profileService;
    private final PreferencesRepository preferencesRepository;

    public Preferences getPreferences(Long preferencesId){
        if(!preferencesRepository.existsById(preferencesId)){
            throw new ResourceNotFoundException("Preferences with id of " + preferencesId + " does not exist");
        }
        return preferencesRepository.findPreferencesById(preferencesId).get();
    }

    public Preferences addPreferences(Long profileId, Preferences preferences){
        Profile profile = profileService.getProfile(profileId);
        preferences.setProfile(profile);
        return preferencesRepository.save(preferences);
    }

}
