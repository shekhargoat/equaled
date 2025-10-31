package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.McpToolSchema;
import com.equaled.controller.mcp.dto.McpToolsResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EqualEdMcpCatalog {

    // Define JSON Schemas once (avoid rebuilding per request)
    /*
    private static final Map<String, Object> CREATE_STUDENT_SCHEMA;
    static {
        CREATE_STUDENT_SCHEMA = new HashMap<>();
        CREATE_STUDENT_SCHEMA.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> name = new HashMap<>();
        name.put("type", "string");
        properties.put("name", name);
        Map<String, Object> email = new HashMap<>();
        email.put("type", "string");
        email.put("format", "email");
        properties.put("email", email);
        Map<String, Object> grade = new HashMap<>();
        grade.put("type", "string");
        properties.put("grade", grade);
        CREATE_STUDENT_SCHEMA.put("properties", properties);
        CREATE_STUDENT_SCHEMA.put("required", Arrays.asList("name", "email"));
    }
    */

    private static final Map<String, Object> GET_STUDENT_SCHEMA;
    static {
        GET_STUDENT_SCHEMA = new HashMap<>();
        GET_STUDENT_SCHEMA.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> email = new HashMap<>();
        email.put("type", "string");
        properties.put("email", email);
        GET_STUDENT_SCHEMA.put("properties", properties);
        GET_STUDENT_SCHEMA.put("required", Collections.singletonList("email"));
    }

    /*
    private static final Map<String, Object> LIST_COURSES_SCHEMA;
    static {
        LIST_COURSES_SCHEMA = new HashMap<>();
        LIST_COURSES_SCHEMA.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> page = new HashMap<>();
        page.put("type", "integer");
        page.put("minimum", 1);
        properties.put("page", page);
        Map<String, Object> size = new HashMap<>();
        size.put("type", "integer");
        size.put("minimum", 1);
        size.put("maximum", 100);
        properties.put("size", size);
        LIST_COURSES_SCHEMA.put("properties", properties);
    }

    private static final Map<String, Object> ENROLL_STUDENT_SCHEMA;
    static {
        ENROLL_STUDENT_SCHEMA = new HashMap<>();
        ENROLL_STUDENT_SCHEMA.put("type", "object");
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> email = new HashMap<>();
        email.put("type", "string");
        properties.put("email", email);
        Map<String, Object> courseId = new HashMap<>();
        courseId.put("type", "string");
        properties.put("course_id", courseId);
        ENROLL_STUDENT_SCHEMA.put("properties", properties);
        ENROLL_STUDENT_SCHEMA.put("required", Arrays.asList("email", "course_id"));
    }
    */

    public McpToolsResponse listTools() {
        List<McpToolSchema> tools = new ArrayList<>();
        tools.add(new McpToolSchema(
                "get_student",
                "Fetch student details by email",
                GET_STUDENT_SCHEMA
        ));
        /*
        tools.add(new McpToolSchema(
            "create_student",
            "Create a new student",
            CREATE_STUDENT_SCHEMA
        ));
        tools.add(new McpToolSchema(
            "list_courses",
            "List available courses (paged)",
            LIST_COURSES_SCHEMA
        ));
        tools.add(new McpToolSchema(
            "enroll_student",
            "Enroll a student into a course",
            ENROLL_STUDENT_SCHEMA
        ));
        */
        return new McpToolsResponse(tools);
    }
}