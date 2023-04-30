package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AvailableFlower extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int availableFlowerKey;

    @ManyToOne
    @JoinColumn(name = "user_key")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flower_key")
    private Flower flower;
}