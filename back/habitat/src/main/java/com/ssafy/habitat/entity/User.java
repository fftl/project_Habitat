package com.ssafy.habitat.entity;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@EnableJpaRepositories
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User extends BaseTime /*implements UserDetails*/ {

    @Id
    private String userKey;
    private String nickname;
    private String imgUrl;
    private String friendCode;
    private int goal;
    private String socialKey;

    @Column(columnDefinition = "TINYINT(1)")
    private int socialType;

    /**
     * 유저가 가져야하는 데이터들
     */
    @OneToMany(mappedBy = "user")
    private List<Planting> plantingList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Collection> collectionList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserFlower> userFlowerList = new ArrayList<>();

    //ok
    @OneToMany(mappedBy = "user")
    private List<UserFlowerLog> userFlowerLogList = new ArrayList<>();

    //ok
    @OneToMany(mappedBy = "user")
    private List<DrinkLog> drinkLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StreakLog> streakLogList = new ArrayList<>();

    @OneToMany(mappedBy = "to")
    private List<FriendRequest> friendRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "myId")
    private List<Friend> friendList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserCoaster userCoaster;

    /**
     * Security impl 데이터들 ================================================================================================
     */
//    @Override
//    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return userKey;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
