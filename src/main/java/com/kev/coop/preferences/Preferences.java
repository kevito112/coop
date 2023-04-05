package com.kev.coop.preferences;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kev.coop.profile.Gender;
import com.kev.coop.profile.Profile;
import com.kev.coop.user.Role;
import com.kev.coop.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Preferences")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Preferences {
    @Id
    private Long id;

    @Column(nullable = false)
    private int mileRadius;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private int lowerLimitAge;

    @Column(nullable = false)
    private int upperLimitAge;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Profile profile;
}
