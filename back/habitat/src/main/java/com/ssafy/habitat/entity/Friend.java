package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Friend extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendKey;

    @ManyToOne
    @JoinColumn
    private User myId;

    @ManyToOne
    @JoinColumn
    private User friendId;
}
