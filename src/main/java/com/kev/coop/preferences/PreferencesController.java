package com.kev.coop.preferences;

import com.kev.coop.preferences.PreferencesService;
import com.kev.coop.security.JwtService;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PreferencesController {
    private final PreferencesService preferencesService;
    private final JwtService jwtService;

    @GetMapping("preferences/{preferencesId}")
    public Preferences getPreferences(@PathVariable Long profileId){
        return preferencesService.getPreferences(profileId);
    }

    @GetMapping("/preferences")
    public List<Preferences> getPreferences(){
        return preferencesService.getPreferences();
    }

    @PutMapping ("/preferences")
    public Preferences createPreferences(@RequestHeader("Authorization") String token, @RequestBody Preferences preferences) {
        Long profileId= Long.valueOf(jwtService.extractUsername(token.substring(7)));
        return preferencesService.addPreferences(profileId, preferences);
    }


}