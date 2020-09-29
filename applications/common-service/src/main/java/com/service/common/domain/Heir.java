package com.service.common.domain;

import com.service.common.enums.HeirStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "heirs")
public class Heir extends Account {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private HeirStatusEnum isHeir;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="files_heirs",
            joinColumns = { @JoinColumn(name = "heir_id") },
            inverseJoinColumns = { @JoinColumn(name = "file_id") })
    private List<File> files;
}
