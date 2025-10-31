package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.McpCallRequest;
import com.equaled.controller.mcp.dto.McpCallResponse;
import com.equaled.controller.mcp.dto.McpToolsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
public class EqualEdMcpControllerV1 {

  private final EqualEdMcpCatalog catalog;
  private final EqualEdMcpDispatcher dispatcher;

  @GetMapping("/tools")
  @Cacheable("mcp-tools") // manifest rarely changes
  public McpToolsResponse tools() {
    return catalog.listTools();
  }

  @PostMapping("/call")
  public ResponseEntity<McpCallResponse> call(@RequestBody McpCallRequest req,
                                              @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader) {
    final String traceId = (traceIdHeader != null && !traceIdHeader.isBlank())
        ? traceIdHeader
        : UUID.randomUUID().toString();
    try {
      Object result = dispatcher.dispatch(req.name(), req.arguments(), traceId);
      return ResponseEntity.ok(McpCallResponse.ok(result, traceId));
    } catch (IllegalArgumentException iae) {
      return ResponseEntity.badRequest().body(
          McpCallResponse.fail("VALIDATION_ERROR", iae.getMessage(), null, traceId)
      );
    } catch (Exception ex) {
      return ResponseEntity.status(502).body(
          McpCallResponse.fail("UPSTREAM_ERROR", "Failed to execute tool", ex.getMessage(), traceId)
      );
    }
  }
}