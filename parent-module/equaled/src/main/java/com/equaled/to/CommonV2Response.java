package com.equaled.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommonV2Response implements Serializable {

    private static final long serialVersionUID = 8066274194975110787L;
    private String id;
    private String createdTime;
    private Map<String,String> fields;

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

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public void putField(String key,String value){
        if(fields==null) fields = new HashMap<>();
        fields.put(key, value);
    }
}
