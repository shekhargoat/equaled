package com.equaled.controller.mcp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McpCallResponse {

  private Object result;
  private McpError error;
  private String traceId;

  /**
   * Factory method to create a successful response
   */
  public static McpCallResponse ok(Object result, String traceId) {
    return new McpCallResponse(result, null, traceId);
  }

  /**
   * Factory method to create a failed response
   */
  public static McpCallResponse fail(String code, String message, Object details, String traceId) {
    return new McpCallResponse(null, new McpError(code, message, details), traceId);
  }
}