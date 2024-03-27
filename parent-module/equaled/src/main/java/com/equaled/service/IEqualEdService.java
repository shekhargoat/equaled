package com.equaled.service;

import com.equaled.to.DashboardTO;

import java.util.List;

public interface IEqualEdService {

    List<DashboardTO> getDashboardsByUser(Integer userId);
    List<DashboardTO> getDashboardsByUser(String userId);
}
