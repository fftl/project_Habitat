package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Log extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logKey;
    private int drink;
    private boolean isCoaster;
    private char drinkType;
    private boolean isRemoved;

    @ManyToOne
    @JoinColumn(name = "user_key")
    private User user;
}
