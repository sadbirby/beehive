package com.beehive.repository;

import java.util.Optional;

import com.beehive.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    Optional<UsersEntity> findByLoginId(String loginId);
}
