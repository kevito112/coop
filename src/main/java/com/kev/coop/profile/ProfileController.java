package com.kev.coop.profile;

import com.kev.coop.security.JwtService;
import com.kev.coop.user.User;
import com.kev.coop.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final JwtService jwtService;

    @GetMapping("profile/{profileId}")
    public Profile getProfile(@PathVariable Long profileId){
        return profileService.getProfile(profileId);
    }

    @GetMapping(value = "user/profile_pic/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadImage(@PathVariable Long id){
        return profileService.downloadImage(id);
    }

    @PostMapping("/profile")
    public Profile createProfile(@RequestHeader("Authorization") String token, @RequestBody Profile profile) {
        Long userId= Long.valueOf(jwtService.extractUsername(token.substring(7)));
        return profileService.addProfile(userId, profile);
    }

}
