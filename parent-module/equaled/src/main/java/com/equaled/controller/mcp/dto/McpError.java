package com.equaled.controller.mcp.dto;

public record McpError(String code, String message, Object details) {}
