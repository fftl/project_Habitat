package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Collection extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int collectionKey;

    @ManyToOne
    @JoinColumn(name = "user_key")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flower_key")
    private Flower flower;
}
