package com.ssafy.habitat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User extends BaseTime{

    @Id
    private String userKey;
    private String nickname;
    private String imgUrl;
    private String friendCode;
    private int goal;
    private String kakaoKey;

    //ok
    @OneToMany(mappedBy = "user")
    private List<Planting> plantingList = new ArrayList<>();;

    //ok
    @OneToMany(mappedBy = "user")
    private List<Collection> collectionList = new ArrayList<>();

    //ok
    @OneToMany(mappedBy = "user")
    private List<AvailableFlower> availableFlowerList = new ArrayList<>();

    //ok
    @OneToMany(mappedBy = "user")
    private List<Log> logList = new ArrayList<>();

    //좀 어려움
    @OneToMany(mappedBy = "to")
    private List<FriendRequest> friendRequestList = new ArrayList<>();

    //ok
    @OneToMany(mappedBy = "myId")
    private List<Friend> friendList = new ArrayList<>();

    //ok
    @OneToOne(mappedBy = "user")
    private UserCoaster userCoaster;
}
