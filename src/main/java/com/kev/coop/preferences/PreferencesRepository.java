package com.kev.coop.preferences;

import com.kev.coop.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    Optional<Preferences> findPreferencesById(Long id);
}