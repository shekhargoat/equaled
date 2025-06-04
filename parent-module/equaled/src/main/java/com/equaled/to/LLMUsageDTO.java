package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LLMUsageDTO {
    private Long id;
    private String user_id;
    private String week_start;
    private String week_end;
    private Integer call_count;
    private Boolean is_premium;
    private String user_type;
    private String created_at;
    private String updated_at;
}
