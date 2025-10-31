package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.EqualEdToolName;
import com.equaled.service.IEqualEdServiceV2;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EqualEdMcpDispatcher {

  private final ObjectMapper mapper;
  private final IEqualEdServiceV2 service;

  /**
   * Dispatches the tool call to the appropriate handler
   */
  public Object dispatch(EqualEdToolName name, Map<String, Object> args, String traceId) {
    // Convert untyped args → typed inputs for safety
      if (Objects.requireNonNull(name) == EqualEdToolName.GET_STUDENT) {
          GetStudentInput input = mapper.convertValue(args, GetStudentInput.class);
          requireNonEmpty(input.getEmail(), "email");
          return service.getUserByEmail(input.getEmail());
          //return equalEdService.getStudent(input.getEmail(), traceId);
      }
      throw new IllegalArgumentException("Unknown tool: " + name);
  }

  /**
   * Validates that a string field is not empty or blank
   */
  private void requireNonEmpty(String v, String field) {
    if (v == null || v.trim().isEmpty()) {
      throw new IllegalArgumentException(field + " is required");
    }
  }

  /**
   * Typed input for safer mapping and validation
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GetStudentInput {
    private String email;
  }

    /*
    private void validate(CreateStudentInput in) {
        requireNonEmpty(in.email(), "email");
        requireNonEmpty(in.name(), "name");
    }
    */

  // Typed inputs for safer mapping
    /*
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateStudentInput {
        private String name;
        private String email;
        private String grade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListCoursesInput {
        private Integer page;
        private Integer size;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnrollStudentInput {
        private String student_id;
        private String course_id;
    }
    */
}