package com.kev.coop.swipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kev.coop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "Swipes")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Swipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long swipeId;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "swiper_id", nullable = false)
    private User swiperUser;

    @Column(nullable = false)
    private boolean wouldDate;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "swipee_id", nullable = false)
    private User swipeeUser;
}
