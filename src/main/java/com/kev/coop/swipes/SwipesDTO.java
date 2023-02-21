package com.kev.coop.swipes;

import com.kev.coop.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class SwipesDTO {
    private Long swipesId;
    private Long userId;
    private boolean wouldDate;

}
