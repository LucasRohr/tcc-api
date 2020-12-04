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
public class Heir {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private HeirStatusEnum status;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "heir")
    private List<FileHeir> fileHeirs;

    public Heir(HeirStatusEnum status, Owner owner, Account account, List<FileHeir> fileHeirs) {
        this.status = status;
        this.owner = owner;
        this.account = account;
        this.fileHeirs = fileHeirs;
    }
}
