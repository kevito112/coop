package com.kev.coop.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kev.coop.exceptions.ResourceNotFoundException;
import com.kev.coop.matches.Matches;
import com.kev.coop.profile.Gender;
import com.kev.coop.profile.Profile;
import com.kev.coop.swipes.Swipes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {     //Model

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "swiperUser", cascade = CascadeType.ALL)
    private List<Swipes> swipes;

    @JsonIgnore
    @OneToMany(mappedBy = "swipeeUser", cascade = CascadeType.ALL)
    private List<Swipes> swipees;

    @JsonIgnore
    @OneToMany(mappedBy = "matchUser1", cascade = CascadeType.ALL)
    private List<Matches> matches;

    @JsonIgnore
    @OneToMany(mappedBy = "matchUser2", cascade = CascadeType.ALL)
    private List<Matches> matches2;

    public User(String email,String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
