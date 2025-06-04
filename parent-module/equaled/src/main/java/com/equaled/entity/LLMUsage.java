package com.equaled.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "llm_usage")
@Data
public class LLMUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "week_start", nullable = false)
    private LocalDateTime weekStart;

    @Column(name = "week_end", nullable = false)
    private LocalDateTime weekEnd;

    @Column(name = "call_count")
    private Integer callCount = 0;

    @Column(name = "is_premium")
    private Boolean isPremium = false;

    @Column(name = "user_type")
    private String userType = "free";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
