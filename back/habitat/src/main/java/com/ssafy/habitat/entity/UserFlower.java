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
public class UserFlower extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFlowerKey;
    private boolean streak;
    private boolean friend;
    private boolean drink;
    private boolean connect;
    private boolean isUnlocked;

    @ManyToOne
    private User user;

    @ManyToOne
    private Flower flower;
}
