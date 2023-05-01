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
    private String get_condition;
    private int max_exp;
    private boolean streak;
    private int streak_value;
    private boolean friend;
    private int friend_value;
    private boolean drink;
    private int drink_value;
    private boolean connect;

    public Flower toEntity() {
        Flower flower = Flower.builder()
                .name(this.name)
                .story(this.story)
                .getCondition(this.get_condition)
                .streak(this.streak)
                .streakValue(this.streak_value)
                .friend(this.friend)
                .friendValue(this.friend_value)
                .drink(this.drink)
                .drinkValue(this.drink_value)
                .connect(this.connect)
                .build();

        return flower;
    }
}
