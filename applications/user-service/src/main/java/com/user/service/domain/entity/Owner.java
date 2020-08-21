package com.user.service.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class Owner extends Account {

    @Column(nullable = false)
    private Boolean isAlive;

}
