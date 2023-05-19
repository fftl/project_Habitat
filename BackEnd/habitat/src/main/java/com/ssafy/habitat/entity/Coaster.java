package com.ssafy.habitat.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Coaster extends BaseTime{

    @Id
    private String coasterKey;
}
