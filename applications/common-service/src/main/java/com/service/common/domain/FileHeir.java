package com.service.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "files_heirs")
public class FileHeir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heir_id", nullable = false)
    private Heir heir;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;
}
