package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Planting extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plantingKey;

    private int flowerCnt;
    private int exp;
    private int lv;

    @ManyToOne
    private User user;

    @ManyToOne
    private Flower flower;
}
