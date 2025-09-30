package com.janseva.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String photoUrl; // In a real app, this would point to a GCS/S3 bucket URL
    private Double latitude;
    private Double longitude;
    private String status = "SUBMITTED";
    private String issueType = "UNCATEGORIZED";

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}