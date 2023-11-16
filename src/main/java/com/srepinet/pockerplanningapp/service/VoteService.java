package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.EndVotingDto;
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
    private final PokerSessionService pokerSessionService;
    private final VoteRepository voteRepository;
    private final UserStoryService userStoryService;
    private final VoteMapper voteMapper;

    public VoteDto applyVote(Long sessionId, VoteDto voteDto) {
        PokerSession sessionById = pokerSessionService.getSessionById(sessionId);
        validateUserStory(sessionId, voteDto);

        Vote voteToSave = voteMapper.dtoToVote(voteDto);
        voteToSave.setSession(sessionById);

        VoteDto result = voteMapper.voteToDto(voteRepository.save(voteToSave));
        result.setIsLastVote(checkIfLastVote(sessionId, voteDto));
        return result;
    }

    private boolean checkIfLastVote(Long sessionId, VoteDto voteDto) {
        long count = voteRepository.findAll().
                stream()
                .filter(vote -> vote.getUserStory().getId().equals(voteDto.getUserStoryId()))
                .count();

        long registeredMembers = userStoryService.findUserStory(voteDto.getUserStoryId())
                .getMembers()
                .stream()
                .count();
        return registeredMembers == count;
    }

    private void validateUserStory(Long sessionId, VoteDto voteDto) {
        UserStory userStory = userStoryService.findUserStory(voteDto.getUserStoryId());

        if (UserStoryStatus.VOTING != userStory.getStatus()) {
            throw new IllegalArgumentException("UserStory status is not " + UserStoryStatus.VOTING);
        }
    }

    public List<VoteDto> endVoting(Long sessionId, EndVotingDto voteDto) {
        updateStoryToVoted(sessionId, voteDto);
        return voteRepository.findAll()
                .stream()
                .filter(vote -> vote.getUserStory().getId().equals(voteDto.getUserStoryId()))
                .map(voteMapper::voteToDto)
                .toList();
    }

    private void updateStoryToVoted(Long sessionId, EndVotingDto endVotingDto) {
        UserStory userStory = userStoryService.findUserStory(endVotingDto.getUserStoryId());
        userStory.setStatus(UserStoryStatus.VOTED);
        userStoryService.saveAndMapToDto(userStory);
    }
}
