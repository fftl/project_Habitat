package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class FriendRequest extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendRequestKey;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @Column(columnDefinition = "TINYINT(1)")
    private int status;
}
