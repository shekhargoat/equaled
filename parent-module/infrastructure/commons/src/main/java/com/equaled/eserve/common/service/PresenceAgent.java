package com.equaled.eserve.common.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PresenceAgent {
    private String projectSid;
    private String agentSid;
    private String timeStamp;
    private String status;
    private String telephonyStatus;
    private String chatStatus;
    private int maxChats;
    private int activeChats;

   }
