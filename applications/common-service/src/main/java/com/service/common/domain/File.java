package com.service.common.domain;

import com.service.common.enums.FileTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(length = 200)
    private String description;

    @Column(nullable = false, name = "bucket_url")
    private String bucketUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileTypeEnum type;

    @Column(nullable = false, name = "mime_type")
    private String mimeType;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "owner_id")
    private Owner owner;

    @Column(nullable = false)
    private boolean isActive = true;

    public File(
        String name,
        String description,
        String bucketUrl,
        FileTypeEnum type,
        String mimeType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Owner owner
    ) {
        this.name = name;
        this.description = description;
        this.bucketUrl = bucketUrl;
        this.type = type;
        this.mimeType = mimeType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.owner = owner;
    }
}
