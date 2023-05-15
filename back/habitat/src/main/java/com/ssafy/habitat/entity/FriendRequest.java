package com.ssafy.habitat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User from;

    @ManyToOne
    @JsonIgnore
    private User to;

    @Column(columnDefinition = "TINYINT(1)")
    private int status;
}
