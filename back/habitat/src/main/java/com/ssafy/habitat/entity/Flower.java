package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Flower extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flowerKey;
    private String name;
    private String story;
    private boolean streak;
    private int streakValue;
    private boolean friend;
    private int friendValue;
    private boolean drink;
    private int drinkValue;
    private boolean connect;

    @OneToMany(mappedBy = "flower")
    private List<AvailableFlower> availableFlowerList = new ArrayList<>();
}
