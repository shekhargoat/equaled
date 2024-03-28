package com.equaled.repository;

import com.equaled.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Override
    Optional<Users> findById(Integer integer);
}