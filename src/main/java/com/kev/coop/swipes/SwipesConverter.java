package com.kev.coop.swipes;

import org.springframework.stereotype.Component;

@Component
public class SwipesConverter {
    public SwipesDTO entityToDTO(Swipes swipes){
        SwipesDTO dto = new SwipesDTO();
        dto.setSwipesId(swipes.getSwipeId());
        dto.setUserId(swipes.getSwiperUser().getId());
        dto.setWouldDate(swipes.isWouldDate());
        return dto;
    }
    /**
    public Swipes dtoToEntity(SwipesDTO dto){
        Swipes swipes = new Swipes();
        swipes.setSwipeId(dto.getSwipeId());
        swipes.setSwiper(dto.getSwiper());
        swipes.setWouldDate(dto.isWouldDate());
        return swipes;
    }
     **/
}
