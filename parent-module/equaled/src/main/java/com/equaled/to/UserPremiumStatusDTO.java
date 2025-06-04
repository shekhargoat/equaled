package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPremiumStatusDTO {
    private Long id;
    private String user_id;
    private Boolean is_premium;
    private String user_type;
    private String premium_start_date;
    private String premium_end_date;
    private String subscription_type;
    private String created_at;
    private String updated_at;
}