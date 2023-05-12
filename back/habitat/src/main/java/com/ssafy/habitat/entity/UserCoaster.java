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
public class UserCoaster extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCoasterKey;

    @OneToOne
    @JsonIgnore
    private User user;

    @OneToOne
    @JsonIgnore
    private Coaster coaster;
}
