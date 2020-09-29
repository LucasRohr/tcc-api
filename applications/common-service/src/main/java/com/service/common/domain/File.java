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

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, name = "bucket_url")
    private String bucketUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileTypeEnum type;

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
