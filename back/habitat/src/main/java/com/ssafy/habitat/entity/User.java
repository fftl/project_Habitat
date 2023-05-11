package com.ssafy.habitat.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User extends BaseTime implements UserDetails {

    @Id
    private String userKey;
    private String nickname;
    private String password;
    private String imgUrl;
    private String friendCode;
    private int goal;
    private String socialKey;
    private String refreshKey;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    /**
     * Security impl 데이터들 ================================================================================================
     */
    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userKey;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "userKey='" + userKey + '\'' +
                ", nickname='" + nickname + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", friendCode='" + friendCode + '\'' +
                ", goal=" + goal +
                ", socialKey='" + socialKey + '\'' +
                ", refreshKey='" + refreshKey + '\'' +
                ", socialType=" + socialType +
                ", plantingList=" + plantingList +
                ", collectionList=" + collectionList +
                ", userFlowerList=" + userFlowerList +
                ", userFlowerLogList=" + userFlowerLogList +
                ", drinkLogList=" + drinkLogList +
                ", streakLogList=" + streakLogList +
                ", friendRequestList=" + friendRequestList +
                ", friendList=" + friendList +
                ", userCoaster=" + userCoaster +
                ", roles=" + roles +
                '}';
    }
}
