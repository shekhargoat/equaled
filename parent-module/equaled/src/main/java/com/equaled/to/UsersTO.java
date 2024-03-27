package com.equaled.to;

import com.equaled.value.EqualEdEnums;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UsersTO extends BaseTO {

    private static final long serialVersionUID = -9221566340996757881L;
    private String username;
    private String password;
    private String email;
    private EqualEdEnums.UserRole role;
    private int enabled;
    private long lastLogin;
    private long lastUpdatedOn;
    private AccountsTO relatedAccount;
    private YearGroupTO yearGroupId;


}
