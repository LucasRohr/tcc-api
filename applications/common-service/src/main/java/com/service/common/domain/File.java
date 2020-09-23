package com.service.common.domain;

import com.service.common.enums.FileTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 60)
    @Column(nullable = false)
    private String name;

    @Size(max = 200)
    private String description;

    @Size(max = 100)
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private FileTypeEnum type;

    @Size(max = 6)
    @Column(nullable = false)
    private String mime_type;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private Owner owner;
}
