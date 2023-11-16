package com.srepinet.pockerplanningapp.repository;

import com.srepinet.pockerplanningapp.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    List<UserStory> findBySessionId(Long sessionId);

    Optional<UserStory> findBySessionIdAndId(Long sessionId, Long id);
}
