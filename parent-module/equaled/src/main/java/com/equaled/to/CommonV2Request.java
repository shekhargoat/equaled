package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Setter @Getter @NoArgsConstructor
public class CommonV2Request implements Serializable {
    private static final long serialVersionUID = 4429312766420443497L;
    private Map<String,String> fields;
}
