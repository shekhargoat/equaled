package com.equaled.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    FREE("free", 50, 40, 47, false),
    STUDENT("student", 75, 60, 71, false),
    PREMIUM("premium", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, true);

    private final String type;
    private final int limit;
    private final int warningThreshold;
    private final int criticalThreshold;
    private final boolean isUnlimited;

    public static UserType fromString(String type) {
        for (UserType ut : values()) {
            if (ut.type.equalsIgnoreCase(type)) {
                return ut;
            }
        }
        return FREE; // default fallback
    }
}