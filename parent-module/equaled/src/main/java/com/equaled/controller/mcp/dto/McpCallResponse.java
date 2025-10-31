package com.equaled.controller.mcp.dto;

public record McpCallResponse(
    Object result,
    McpError error,
    String traceId
) {
  public static McpCallResponse ok(Object result, String traceId) {
    return new McpCallResponse(result, null, traceId);
  }
  public static McpCallResponse fail(String code, String message, Object details, String traceId) {
    return new McpCallResponse(null, new McpError(code, message, details), traceId);
  }
}
