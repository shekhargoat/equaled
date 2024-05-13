package com.equaled.repository;

import com.equaled.entity.Accounts;
import com.equaled.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Accounts, Integer> {

    @Override
    Optional<Accounts> findById(Integer integer);
}
