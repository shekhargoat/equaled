package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.EqualEdToolName;
import com.equaled.service.IEqualEdServiceV2;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EqualEdMcpDispatcher {

  private final ObjectMapper mapper;
  private final IEqualEdServiceV2 service;

  public Object dispatch(EqualEdToolName name, Map<String, Object> args, String traceId) {
    // Convert untyped args → typed inputs for safety
    return switch (name) {
      case GET_STUDENT -> {
        var in = mapper.convertValue(args, GetStudentInput.class);
        requireNonEmpty(in.email(), "email");
        yield service.getUserByEmail(in.email());
//        yield equalEdService.getStudent(in.email(), traceId);
      }
    };
  }

  private void requireNonEmpty(String v, String field) {
    if (v == null || v.isBlank()) {
      throw new IllegalArgumentException(field + " is required");
    }
  }

  public record GetStudentInput(String email) {}

/*  private void validate(CreateStudentInput in) {
    requireNonEmpty(in.name(), "name");
    requireNonEmpty(in.email(), "email");
  }*/


  // Typed inputs for safer mapping
//  public record CreateStudentInput(String name, String email, String grade) {}
//  public record ListCoursesInput(Integer page, Integer size) {}
//  public record EnrollStudentInput(String student_id, String course_id) {}
}