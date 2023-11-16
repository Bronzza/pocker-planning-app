package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.VoteDto;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.entity.Vote;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import com.srepinet.pockerplanningapp.mapper.VoteMapper;
import com.srepinet.pockerplanningapp.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PokerSessionService pokerSessionService;
    private final UserStoryService userStoryService;
    private final MemberService memberService;
    private final VoteMapper voteMapper;

    public List<VoteDto> findAll(Long sessionId) {
        return voteRepository.findBySessionId(sessionId)
                .stream()
                .map(voteMapper::voteToDto)
                .toList();
    }

    public VoteDto applyVote(Long sessionId, VoteDto voteDto) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);

        validateMember(sessionId, voteDto);
        validateUserStory(sessionId, voteDto);

        Vote voteToSave = voteMapper.dtoToVote(voteDto);
        voteToSave.setSession(session);

        return voteMapper.voteToDto(voteRepository.save(voteToSave));
    }

    private void validateUserStory(Long sessionId, VoteDto voteDto) {
        UserStory userStory = userStoryService.findUserStory(sessionId, voteDto.getUserStoryId());

        if (UserStoryStatus.VOTING != userStory.getStatus()) {
            throw new IllegalArgumentException("UserStory status is not " + UserStoryStatus.VOTING);
        }
    }

    private void validateMember(Long sessionId, VoteDto voteDto) {
        memberService.findMember(sessionId, voteDto.getMemberId());
    }

    public List<VoteDto>  endVoting(Long sessionId, VoteDto voteDto) {
        updateStoryToVoted(sessionId, voteDto);
        return  findAll(sessionId);
    }

    private void updateStoryToVoted(Long sessionId, VoteDto voteDto) {
        Long userStoryId = voteDto.getUserStoryId();
        if (userStoryId == null) {
            throw new IllegalArgumentException("User story id is not specified");
        }
        UserStory userStory = userStoryService.findUserStory(sessionId, voteDto.getUserStoryId());
        userStory.setStatus(UserStoryStatus.VOTED);
        userStoryService.updateUserStory(userStory);
    }
}
