package com.equaled.repository;

import com.equaled.entity.Users;
import com.equaled.value.EqualEdEnums;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Override
    Optional<Users> findById(Integer integer);
    Optional<Users> findByUsernameIs(String username);

    @Query(value = "select u from Users u where u.sid = decode(:sid,'hex')")
    Optional<Users> findBySid(String sid);

    Optional<Users> findByEmailIs(String email);

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmailAndRole(String email, EqualEdEnums.UserRole role);

    @Query("SELECT u.role, COUNT(u) FROM Users u GROUP BY u.role")
    List<Object[]> countUsersByRole();
}
