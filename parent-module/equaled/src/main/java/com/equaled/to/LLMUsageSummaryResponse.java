package com.equaled.to;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LLMUsageSummaryResponse {
    private int current_usage;
    private Object limit; // "unlimited" or int
    private Object remaining; // "unlimited" or int
    private boolean limit_reached;
    private double percentage_used;
    private String user_type;
    private boolean is_premium;
    private boolean is_unlimited;
    private String subscription_type;
    private Integer warning_threshold;
    private Integer critical_threshold;
    private Boolean is_warning;
    private Boolean is_critical;
    private LocalDateTime week_start;
    private LocalDateTime week_end;
    private int days_until_reset;
}
