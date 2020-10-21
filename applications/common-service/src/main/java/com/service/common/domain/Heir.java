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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private HeirStatusEnum status;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="files_heirs",
            joinColumns = { @JoinColumn(name = "heir_id") },
            inverseJoinColumns = { @JoinColumn(name = "file_id") })
    private List<File> files;

    public Heir(HeirStatusEnum status, Owner owner, Account account, List<File> files) {
        this.status = status;
        this.owner = owner;
        this.account = account;
        this.files = files;
    }
}
