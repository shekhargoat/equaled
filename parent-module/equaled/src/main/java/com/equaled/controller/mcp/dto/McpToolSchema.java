package com.equaled.controller.mcp.dto;

import java.util.Map;

public record McpToolSchema(
    String name,
    String description,
    Map<String, Object> input_schema
) {}
