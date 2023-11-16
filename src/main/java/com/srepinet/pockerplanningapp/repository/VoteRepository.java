package com.srepinet.pockerplanningapp.repository;

import com.srepinet.pockerplanningapp.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
