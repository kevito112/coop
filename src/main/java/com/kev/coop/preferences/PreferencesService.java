package com.kev.coop.preferences;

import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.profile.Profile;
import com.kev.coop.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferencesService {
    private final ProfileService profileService;
    private final PreferencesRepository preferencesRepository;

    @Autowired
    public PreferencesService(PreferencesRepository preferencesRepository, ProfileService profileService) {
        this.preferencesRepository = preferencesRepository;
        this.profileService = profileService;
    }

    public Preferences getPreferences(Long preferencesId){
        if(!preferencesRepository.existsById(preferencesId)){
            throw new ResourceNotFoundException("Preferences with id of " + preferencesId + " does not exist");
        }
        return preferencesRepository.findPreferencesById(preferencesId).get();
    }

    public List<Preferences> getPreferences(){
        return preferencesRepository.findAll();
    }

    public Preferences addPreferences(Long preferenceId, Preferences preferences){
        Profile profile = profileService.getProfile(preferenceId);
        preferences.setProfile(profile);
        return preferencesRepository.save(preferences);
    }

    public Preferences updatePreferences(Long preferenceId, Preferences preferences){
        Preferences existingPreference = preferencesRepository.findPreferencesById(preferenceId).get();
        try{
            existingPreference.setMileRadius(preferences.getMileRadius());
        }catch(NullPointerException e){}
        try{
            existingPreference.setGender(preferences.getGender());
        }catch(NullPointerException e){}
        try{
            existingPreference.setLowerLimitAge(preferences.getLowerLimitAge());
        }catch(NullPointerException e){}
        try{
            existingPreference.setUpperLimitAge(preferences.getUpperLimitAge());
        }catch(NullPointerException e){}
        return preferencesRepository.save(existingPreference);
    }

}
