package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter @Setter @NoArgsConstructor
public class CreateProfileRequest implements Serializable {
    private static final long serialVersionUID = 7756690328641708587L;
    private List<CommonV2Request> records;
    private boolean typecast;
}
