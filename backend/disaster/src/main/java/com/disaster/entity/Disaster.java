package com.disaster.entity;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "disasters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String locationName;

    @Column(columnDefinition = "geography(Point, 4326)")
    private Point location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Type(ListArrayType.class)
    @Column(name = "tags", columnDefinition = "text[]")
    private List<String> tags;

    private String ownerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String auditTrail;

    @OneToMany(mappedBy = "disaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @OneToMany(mappedBy = "disaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;
} 