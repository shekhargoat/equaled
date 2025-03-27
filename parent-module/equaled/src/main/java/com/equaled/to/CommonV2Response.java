package com.equaled.to;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommonV2Response implements Serializable {

    private static final long serialVersionUID = 8066274194975110787L;
    private String id;
    private String createdTime;
    private Map<String, Object> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public void putField(String key,Object value){
        if(fields==null) fields = new HashMap<>();
        fields.put(key, value);
    }
}
