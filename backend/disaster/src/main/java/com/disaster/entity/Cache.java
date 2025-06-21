package com.disaster.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "cache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cache {

    @Id
    private String key;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String value;

    private LocalDateTime expiresAt;
} 