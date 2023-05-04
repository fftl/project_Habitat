package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
