package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LLMUsageWrapperDTO {
    private List<LLMUsageDTO> llm_usage;
    private List<UserPremiumStatusDTO> user_premium_status;
}
