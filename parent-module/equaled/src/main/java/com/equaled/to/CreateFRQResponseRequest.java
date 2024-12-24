package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class CreateFRQResponseRequest implements Serializable {
    private static final long serialVersionUID = -5518779469131235068L;

    private List<CommonV2Request> records;
}
