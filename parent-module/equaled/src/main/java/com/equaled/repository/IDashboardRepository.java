package com.equaled.repository;

import com.equaled.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDashboardRepository extends JpaRepository<Dashboard,Integer> {

        List<Dashboard> findDashboardsByUser(Integer userId);
        @Query(value = "select d from Dashboard d where hex(d.user.sid) = :sid")
        List<Dashboard> findDashboardsByUser(String sid);

}
