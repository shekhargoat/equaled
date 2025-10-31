package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.McpToolSchema;
import com.equaled.controller.mcp.dto.McpToolsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EqualEdMcpCatalog {

  // Define JSON Schemas once (avoid rebuilding per request)
/*
  private static final Map<String, Object> CREATE_STUDENT_SCHEMA = Map.of(
      "type", "object",
      "properties", Map.of(
          "name", Map.of("type", "string"),
          "email", Map.of("type", "string", "format", "email"),
          "grade", Map.of("type", "string")
      ),
      "required", List.of("name", "email")
  );
*/

  private static final Map<String, Object> GET_STUDENT_SCHEMA = Map.of(
      "type", "object",
      "properties", Map.of("email", Map.of("type", "string")),
      "required", List.of("email")
  );

 /* private static final Map<String, Object> LIST_COURSES_SCHEMA = Map.of(
      "type", "object",
      "properties", Map.of(
          "page", Map.of("type", "integer", "minimum", 1),
          "size", Map.of("type", "integer", "minimum", 1, "maximum", 100)
      )
  );

  private static final Map<String, Object> ENROLL_STUDENT_SCHEMA = Map.of(
      "type", "object",
      "properties", Map.of(
          "email", Map.of("type", "string"),
          "course_id", Map.of("type", "string")
      ),
      "required", List.of("email", "course_id")
  );
*/
  public McpToolsResponse listTools() {
    return new McpToolsResponse(List.of(
        new McpToolSchema(
                    "get_student",
                    "Fetch student details by email",
                    GET_STUDENT_SCHEMA
        )/*,
        new McpToolSchema(
            "create_student",
            "Create a new student",
            CREATE_STUDENT_SCHEMA
        ),
        new McpToolSchema(
            "list_courses",
            "List available courses (paged)",
            LIST_COURSES_SCHEMA
        ),
        new McpToolSchema(
            "enroll_student",
            "Enroll a student into a course",
            ENROLL_STUDENT_SCHEMA
        )*/
    ));
  }
}