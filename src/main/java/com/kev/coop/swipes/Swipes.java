package com.kev.coop.swipes;

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

    @ManyToOne
    @JoinColumn(name = "swiper_id", nullable = false)
    private User swiperUser;

    @Column(nullable = false)
    private boolean wouldDate;

    @ManyToOne
    @JoinColumn(name = "swipee_id", nullable = true)
    private User swipeeUser;
}
