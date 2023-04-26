package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class UserCoaster extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCoasterKey;

    @OneToOne
    @JoinColumn(name = "user_key")
    private User user;

    @OneToOne
    @JoinColumn(name = "coaster_key")
    private Coaster coaster;
}
