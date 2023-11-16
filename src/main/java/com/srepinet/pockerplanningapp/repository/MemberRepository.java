package com.srepinet.pockerplanningapp.repository;

import com.srepinet.pockerplanningapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    List<Member> findBySessionId(Long sessionId);

//    Optional<Member> findBySessionIdAndId(Long sessionId, Long id);
}
