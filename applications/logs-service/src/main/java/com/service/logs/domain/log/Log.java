package com.service.logs.domain.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column()
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Log(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }

}
