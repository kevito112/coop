package com.kev.coop.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kev.coop.preferences.Preferences;
import com.kev.coop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String bio;

    @Column(nullable = false)
    private LocalDate dob;

    @Transient
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String location;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private Preferences preferences;

    @Lob
    @Column(name = "profile_pic",length = 1000000)
    private byte[] profilePic;

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
