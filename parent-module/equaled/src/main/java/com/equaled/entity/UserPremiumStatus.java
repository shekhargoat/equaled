package com.equaled.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_premium_status")
@Data
public class UserPremiumStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "is_premium")
    private Boolean isPremium = false;

    @Column(name = "user_type")
    private String userType = "free";

    @Column(name = "premium_start_date")
    private LocalDateTime premiumStartDate;

    @Column(name = "premium_end_date")
    private LocalDateTime premiumEndDate;

    @Column(name = "subscription_type")
    private String subscriptionType = "free";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
