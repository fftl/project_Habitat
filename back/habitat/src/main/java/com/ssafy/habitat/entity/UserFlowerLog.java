package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class UserFlowerLog extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userFlowerLogKey;
    private char mission;

    @ManyToOne
    private User user;

    @ManyToOne
    private Flower flower;

}
