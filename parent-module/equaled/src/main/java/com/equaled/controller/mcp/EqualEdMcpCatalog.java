package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.McpToolSchema;
import com.equaled.controller.mcp.dto.McpToolsResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EqualEdMcpCatalog {

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

    private static final Map<String, Object> CREATE_DASHBOARD_SCHEMA;
    static {
        CREATE_DASHBOARD_SCHEMA = new HashMap<>();
        CREATE_DASHBOARD_SCHEMA.put("type", "object");

        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> userIdType = new HashMap<>();
        userIdType.put("type", "string");
        properties.put("user_id", userIdType);

        Map<String, Object> subjectType = new HashMap<>();
        subjectType.put("type", "string");
        properties.put("subject_name", subjectType);

        Map<String, Object> examIdType = new HashMap<>();
        examIdType.put("type", "string");
        properties.put("exam_id", examIdType);

        Map<String, Object> titleType = new HashMap<>();
        titleType.put("type", "string");
        properties.put("title", titleType);

        CREATE_DASHBOARD_SCHEMA.put("properties", properties);
        CREATE_DASHBOARD_SCHEMA.put("required", Arrays.asList("user_id", "subject_name", "exam_id", "title"));
    }

    public McpToolsResponse listTools() {
        List<McpToolSchema> tools = new ArrayList<>();
        tools.add(new McpToolSchema("get_student", "Fetch student details by email", GET_STUDENT_SCHEMA));
        tools.add(new McpToolSchema("create_student", "Create a new student", CREATE_STUDENT_SCHEMA));
        tools.add(new McpToolSchema("create_dashboard", "Create a dashboard for a user", CREATE_DASHBOARD_SCHEMA));
        return new McpToolsResponse(tools);
    }
}