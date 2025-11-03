package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.McpCallRequest;
import com.equaled.controller.mcp.dto.McpCallResponse;
import com.equaled.controller.mcp.dto.McpToolsResponse;
import com.equaled.eserve.common.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
public class EqualEdMcpControllerV1 {

  private final EqualEdMcpCatalog catalog;
  private final EqualEdMcpDispatcher dispatcher;

  /**
   * GET /mcp/tools - Returns the list of available tools
   * Cached since tool manifest rarely changes
   */
  @GetMapping("/tools")
  @Cacheable("mcp-tools") // manifest rarely changes
  public McpToolsResponse tools() {
    return catalog.listTools();
  }

  /**
   * POST /mcp/call - Executes a tool call
   *
   * @param req The tool call request with name and arguments
   * @param traceIdHeader Optional trace ID header for request tracking
   * @return Response with result or error details
   */
  @PostMapping("/call")
  public ResponseEntity<McpCallResponse> call(@RequestBody McpCallRequest req, @RequestHeader(value = "X-Trace-Id", required = false) String traceIdHeader) {
    final String traceId = (traceIdHeader != null && !traceIdHeader.trim().isEmpty()) ? traceIdHeader : UUID.randomUUID().toString();
    try {
      Object result = dispatcher.dispatch(req.getName(), req.getArguments(), traceId);
      return ResponseEntity.ok(McpCallResponse.ok(result, traceId));
    } catch (IllegalArgumentException iae) {
      return ResponseEntity.badRequest().body(McpCallResponse.fail("VALIDATION_ERROR", iae.getMessage(), null, traceId));
    } catch (RecordNotFoundException rnfe) {
      return ResponseEntity.status(404).body(McpCallResponse.fail("NOT_FOUND", rnfe.getMessage(), null, traceId));
    } catch (Exception ex) {
      String errorMsg = ex.getMessage();
      if (ex.getCause() != null) errorMsg += " | Cause: " + ex.getCause().getMessage();
      return ResponseEntity.status(502).body(McpCallResponse.fail("UPSTREAM_ERROR", "Failed to execute tool", errorMsg, traceId));
    }
  }
}