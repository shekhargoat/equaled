package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.Dashboard;
import com.equaled.eserve.common.DateTimeFrameUtils;
import com.equaled.eserve.common.DateUtils;
import com.equaled.repository.IDashboardRepository;
import com.equaled.service.IEqualEdService;
import com.equaled.service.IEqualEdServiceV2;
import com.equaled.to.CommonV2Response;
import com.equaled.to.DashboardTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImplV2 implements IEqualEdServiceV2 {

    IDashboardRepository dashboardRepository;
    DozerUtils mapper;

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());

        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(dashboard -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(dashboard.getStringSid());
            commonV2Response.putField("exam_id", dashboard.getExamId());
            commonV2Response.putField("user_id",String.valueOf(dashboard.getUser().getId()));
            commonV2Response.putField("subject_name",dashboard.getSubject().getName());
            commonV2Response.putField("title", dashboard.getTitle());
            commonV2Response.putField("start_time",
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(dashboard.getStartTime()),
                            ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);
    }

    @Override
    public Map<String,List<CommonV2Response>> getDashboardsByUser(String userId) {
        log.trace("Finding Dashboard By User: {}",userId);
        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserSid(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        List<CommonV2Response> commonV2Responses = dashboardsByUserId.stream().map(dashboard -> {
            CommonV2Response commonV2Response = new CommonV2Response();
            commonV2Response.setId(dashboard.getStringSid());
            commonV2Response.putField("exam_id", dashboard.getExamId());
            commonV2Response.putField("user_id",String.valueOf(dashboard.getUser().getId()));
            commonV2Response.putField("subject_name",dashboard.getSubject().getName());
            commonV2Response.putField("title", dashboard.getTitle());
            commonV2Response.putField("start_time",
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(dashboard.getStartTime()),
                            ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return commonV2Response;
        }).collect(Collectors.toList());


        return generateResponse(commonV2Responses);    }

    public Map<String,List<CommonV2Response>> generateResponse(List<CommonV2Response> commonV2Responses){
        Map<String,List<CommonV2Response>> response = new HashMap<>();
        response.put("records",commonV2Responses);
        return response;
    }
}
