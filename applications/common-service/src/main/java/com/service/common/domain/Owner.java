package com.service.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "owners")
public class Owner extends Account {

    @Column(nullable = false)
    private Boolean isAlive;

}
