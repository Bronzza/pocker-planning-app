package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.entity.Member;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.mapper.MemberMapper;
import com.srepinet.pockerplanningapp.mapper.PokerSessionMapper;
import com.srepinet.pockerplanningapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PokerSessionService pokerSessionService;
    private final MemberMapper memberMapper;
    private final PokerSessionMapper pokerSessionMapper;

    public List<MemberDto> findAll(Long sessionId) {
//        PokerSession session = pokerSessionService.getSessionById(sessionId);
        return memberRepository.findAll()
                .stream()
                .filter(member -> member.getSessions().stream().map(PokerSession::getId).toList().contains(sessionId))
                .map(memberMapper::memberToDto)
                .toList();

//        return memberRepository.findBySessionId(session.getId())
//                .stream()
//                .map(memberMapper::memberToDto)
//                .toList();
    }

    public Member findMember(Long sessionId, Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public MemberDto joinSession(Long sessionId, MemberDto memberDto) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        Member member = memberMapper.dtoToMember(memberDto);
        List<PokerSession> pokerSessions = new ArrayList<>();
        pokerSessions.add(session);
        member.setSessions(pokerSessions);
        return memberMapper.memberToDto(memberRepository.save(member));
    }

    public void logout(Long sessionId, Long memberId) {
        Member member = findMember(sessionId, memberId);
        memberRepository.delete(member);
        return;
    }
}
