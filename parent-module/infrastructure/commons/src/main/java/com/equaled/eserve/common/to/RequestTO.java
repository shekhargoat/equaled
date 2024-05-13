package com.equaled.eserve.common.to;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class RequestTO {
    private String projectSid;
    private List<String> agentSids;
    private List<String> ticketStates;
}