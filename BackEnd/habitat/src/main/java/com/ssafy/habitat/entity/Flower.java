package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Flower extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flowerKey;
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

}
