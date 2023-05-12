package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class DrinkLog extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int drinkLogKey;
    private int drink;
    private boolean isCoaster;
    private char drinkType;
    private boolean isRemoved;

    @ManyToOne
    private User user;
}
