package com.equaled.controller.mcp.dto;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record McpCallRequest(
    @NotNull EqualEdToolName name,
    Map<String, Object> arguments
) {}

