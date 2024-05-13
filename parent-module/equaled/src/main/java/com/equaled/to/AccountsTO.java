package com.equaled.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AccountsTO extends BaseTO {

    private static final long serialVersionUID = -5032121764487886850L;
    private String name;
    private String description;
    private Integer enabled;
    private long createdOn;
    private long lastUpdatedOn;

}
