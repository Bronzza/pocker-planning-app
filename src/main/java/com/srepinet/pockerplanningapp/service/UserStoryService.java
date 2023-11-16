package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.entity.Member;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import com.srepinet.pockerplanningapp.mapper.MemberMapper;
import com.srepinet.pockerplanningapp.mapper.UserStoriesMapper;
import com.srepinet.pockerplanningapp.repository.UserStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final PokerSessionService pokerSessionService;
    private final UserStoriesMapper userStoriesMapper;
    private final MemberMapper memberMapper;

    public List<UserStoryDto> findAll(Long sessionId) {
        return userStoryRepository.findAll()
                .stream()
                .filter(story -> story.getSessions().stream().map(PokerSession::getId).toList().contains(sessionId))
                .map(userStoriesMapper::userStoryToDto)
                .toList();
    }

    public UserStory findUserStory(Long sessionId, Long id) {
        return userStoryRepository.findById(id).orElseThrow();
    }

    public UserStoryDto createStory(Long sessionId, UserStoryDto userStoryDto) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        UserStory storyToSave = userStoriesMapper.dtoToUserStory(userStoryDto);
        ArrayList<PokerSession> sessions = new ArrayList<>();
        sessions.add(session);
        storyToSave.setSessions(sessions);
        return userStoriesMapper.userStoryToDto(userStoryRepository.save(storyToSave));
    }

    public UserStoryDto updateUserStory(Long sessionId, Long id, UserStoryDto userStory) {
        UserStory storyToUpdate = findUserStory(sessionId, id);
        if (userStory.getDescription() != null) {
            storyToUpdate.setDescription(userStory.getDescription());
        } else if (userStory.getStatus() != null) {
            storyToUpdate.setStatus(userStory.getStatus());
        }
        return userStoriesMapper.userStoryToDto(userStoryRepository.save(storyToUpdate));
    }

    public void startVotingStory(Long sessionId, Long id) {
        UserStory storyToUpdate = findUserStory(sessionId, id);
        storyToUpdate.setStatus(UserStoryStatus.VOTING);
        userStoryRepository.save(storyToUpdate);
    }

    public UserStory deleteUserStory(Long sessionId, Long id) {
        UserStory storyToDelete = findUserStory(sessionId, id);
        if (UserStoryStatus.PENDING != storyToDelete.getStatus()) {
            throw new IllegalArgumentException("UserStory is not in " + UserStoryStatus.PENDING + " status");
        }
        userStoryRepository.delete(storyToDelete);
        return storyToDelete;
    }

    public UserStoryDto updateUserStory(UserStory userStory) {
        return userStoriesMapper.userStoryToDto(userStoryRepository.save(userStory));
    }

    public void enterStory(Long sessionId, Long storyId, MemberDto memberDto) {
        //todo can do session validation (if session exist)

        Optional<UserStory> userStoryOptional = userStoryRepository.findById(storyId);
        if (userStoryOptional.isPresent()) {
            UserStory userStory = userStoryOptional.get();
            Set<Member> members = userStory.getMembers();
            if (members.isEmpty()) {
                members = new HashSet<>();
            }
            members.add(memberMapper.dtoToMember(memberDto));
            userStory.setMembers(members);
            userStoryRepository.save(userStory);
        } else {
            throw new IllegalArgumentException("User story with doesn't exist");
        }

    }
}
