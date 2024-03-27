package com.equaled.repository;

import com.equaled.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDashboardRepository extends JpaRepository<Dashboard,Integer> {

        @Query(value = "select d from Dashboard d where d.user.id = :id")
        List<Dashboard> findDashboardsByUserId(Integer id);
        @Query(value = "select d from Dashboard d where hex(d.user.sid) = :sid")
        List<Dashboard> findDashboardsByUserSid(String sid);

}
