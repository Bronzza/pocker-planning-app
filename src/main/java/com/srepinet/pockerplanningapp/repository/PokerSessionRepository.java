package com.srepinet.pockerplanningapp.repository;

import com.srepinet.pockerplanningapp.entity.PokerSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokerSessionRepository extends JpaRepository<PokerSession, Long> {
}
