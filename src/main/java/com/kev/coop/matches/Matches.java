package com.kev.coop.matches;

import com.kev.coop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Matches")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Matches{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "match_user_id", nullable = false)
    private User matchUser1;

    @ManyToOne
    @JoinColumn(name = "match_user_id2", nullable = false)
    private User matchUser2;
}
