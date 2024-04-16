package com.equaled.repository;

import com.equaled.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Override
    Optional<Users> findById(Integer integer);
    Optional<Users> findByUsernameIs(String username);

    @Query(value = "select u from Users u where hex(u.sid) = :sid")
    Optional<Users> findBySid(String sid);

    Optional<Users> findByEmailIs(String email);
}
