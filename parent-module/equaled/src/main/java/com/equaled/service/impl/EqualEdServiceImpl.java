package com.equaled.service.impl;

import com.equaled.dozer.DozerUtils;
import com.equaled.entity.Dashboard;
import com.equaled.repository.IDashboardRepository;
import com.equaled.service.IEqualEdService;
import com.equaled.to.DashboardTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EqualEdServiceImpl implements IEqualEdService {

    IDashboardRepository dashboardRepository;
    DozerUtils mapper;

    @Override
    public List<DashboardTO> getDashboardsByUser(Integer userId) {
        log.trace("Finding Dashboard By User: {}",userId);

        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserId(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        return mapper.convertList(dashboardsByUserId, DashboardTO.class);
    }

    @Override
    public List<DashboardTO> getDashboardsByUser(String userId) {
        log.trace("Finding Dashboard By User: {}",userId);
        List<Dashboard> dashboardsByUserId = Optional.ofNullable(dashboardRepository.findDashboardsByUserSid(userId))
                .orElse(ListUtils.EMPTY_LIST);
        log.debug("Dashboard fetched for User {} = {}",userId, dashboardsByUserId.size());
        return mapper.convertList(dashboardsByUserId, DashboardTO.class);
    }
}
