package com.equaled.controller.mcp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpCallRequest {

    @NotNull
    private EqualEdToolName name;

    private Map<String, Object> arguments;
}
