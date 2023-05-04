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
    @JoinColumn(name = "from_key")
    private User from;

    @ManyToOne
    @JoinColumn(name = "to_key")
    private User to;

    @Column(columnDefinition = "TINYINT(1)")
    private int status;
}
