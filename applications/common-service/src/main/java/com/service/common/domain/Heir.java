package com.service.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class Heir extends Account {

    @Column(name = "is_heir")
    private String isHeir;
}
