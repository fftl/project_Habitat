package com.ssafy.habitat.dto;

import com.ssafy.habitat.entity.Flower;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestFlowerDto {
    private String name;
    private String story;
    private String getCondition;
    private int maxExp;
    private boolean streak;
    private int streakValue;
    private boolean friend;
    private int friendValue;
    private boolean drink;
    private int drinkValue;
    private boolean connect;

    public Flower toEntity() {
        Flower flower = Flower.builder()
                .name(this.name)
                .story(this.story)
                .getCondition(this.getCondition)
                .streak(this.streak)
                .streakValue(this.streakValue)
                .friend(this.friend)
                .friendValue(this.friendValue)
                .drink(this.drink)
                .drinkValue(this.drinkValue)
                .connect(this.connect)
                .build();

        return flower;
    }
}
