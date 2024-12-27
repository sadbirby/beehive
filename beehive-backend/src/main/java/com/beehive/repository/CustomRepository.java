package com.beehive.repository;

import com.beehive.dto.PostDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<PostDto> findAllPosts(PageRequest pageRequest, String username) {
        
    }

}
