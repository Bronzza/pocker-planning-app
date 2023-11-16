package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.dto.MemberStoryDto;
import com.srepinet.pockerplanningapp.entity.Member;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.mapper.MemberMapper;
import com.srepinet.pockerplanningapp.mapper.PokerSessionMapper;
import com.srepinet.pockerplanningapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PokerSessionService pokerSessionService;
    private final MemberMapper memberMapper;
    private final UserStoryService userStoryService;

    public List<MemberDto> findAll(Long sessionId) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        return session.getMembers()
                .stream().toList()
                .stream()
                .map(memberMapper::memberToDto)
                .toList();
    }

    public Member findMember(Long sessionId, Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public MemberDto joinSession(Long sessionId, MemberDto memberDto) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        Member member = memberMapper.dtoToMember(memberDto);
        Member savedMember = memberRepository.save(member);
        List<Member> members = session.getMembers();
        if (members.isEmpty()) {
            session.setMembers(new ArrayList<>());
        }
        session.getMembers().add(savedMember);
        pokerSessionService.saveSession(session);
        return memberMapper.memberToDto(savedMember);
    }

    public void logout(Long sessionId, Long memberId) {
        Member member = findMember(sessionId, memberId);
        memberRepository.delete(member);
    }

    public void enterStory(Long sessionId, MemberStoryDto memberStoryDto) {
        UserStory userStory = userStoryService.findUserStory(memberStoryDto.getUserStoryId());
        Member memberToAdd = findMember(memberStoryDto.getMemberId());

//      At the moment we don't have direct link to userStory, we access only from session.
//      User already exist and attached to session.
        validateSession(sessionId, userStory, memberToAdd);

        if (userStory.getMembers().isEmpty()) {
            userStory.setMembers(new ArrayList<>());
        }
        userStory.getMembers().add(memberToAdd);
        userStoryService.saveAndMapToDto(userStory);
    }

    private void validateSession(Long sessionId, UserStory userStory, Member memberToAdd) {
        PokerSession sessionById = pokerSessionService.getSessionById(sessionId);
        boolean storyInSession = sessionById.getUserStories().contains(userStory);
        boolean memberInSession = sessionById.getMembers().contains(memberToAdd);
        if (!storyInSession) {
            throw new IllegalArgumentException("Story is not attached to current planing session");
        }

        if (!memberInSession) {
            throw new IllegalArgumentException("Story is not attached to current planing session");
        }
    }

    public Member updateMember (Member member) {
       return memberRepository.save(member);
    }
}
