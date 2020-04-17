package com.example.csgs.dao;


import com.example.csgs.entity.UserEntity;
import com.example.csgs.entity.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserDao extends PagingAndSortingRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long uid);

    Optional<UserEntity> findOneByUserID(String userAccount);

    Optional<UserEntity> findByUserProfile(UserProfile userProfile);
}
