package com.equaled.controller.mcp;

import com.equaled.controller.mcp.dto.EqualEdToolName;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Request;
import com.equaled.to.CreateProfileRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EqualEdMcpDispatcher {

    private final ObjectMapper mapper;
    private final IEqualEdServiceV2 service;

    public Object dispatch(EqualEdToolName name, Map<String, Object> args, String traceId) {
        switch (Objects.requireNonNull(name)) {
            case GET_STUDENT: {
                GetStudentInput input = mapper.convertValue(args, GetStudentInput.class);
                requireNonEmpty(input.getEmail(), "email");
                return service.getUserByEmail(input.getEmail());
            }
            case CREATE_STUDENT: {
                Map<String, String> fields = new HashMap<>();
                for (Map.Entry<String, Object> entry : args.entrySet()) {
                    fields.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
                }
                CommonV2Request record = new CommonV2Request();
                record.setFields(fields);
                CreateProfileRequest request = new CreateProfileRequest();
                request.setRecords(Collections.singletonList(record));
                return service.createProfile(request);
            }
            default:
                throw new IllegalArgumentException("Unknown tool: " + name);
        }
    }

    private void requireNonEmpty(String v, String field) {
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class GetStudentInput { private String email; }
}