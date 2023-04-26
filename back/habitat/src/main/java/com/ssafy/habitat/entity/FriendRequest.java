package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class FriendRequest extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendRequestKey;

    @ManyToOne
    @JoinColumn(name = "from_key")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_key")
    private User to;

    private int status;

}
