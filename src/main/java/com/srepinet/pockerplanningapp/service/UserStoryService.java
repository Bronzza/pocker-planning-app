package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.entity.Member;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import com.srepinet.pockerplanningapp.mapper.UserStoriesMapper;
import com.srepinet.pockerplanningapp.repository.UserStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final PokerSessionService pokerSessionService;
    private final UserStoriesMapper userStoriesMapper;

    public List<UserStoryDto> findAll(Long sessionId) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        return session.getUserStories()
                .stream().toList()
                .stream()
                .map(userStoriesMapper::userStoryToDto)
                .toList();
    }

    public UserStoryDto createStory(Long sessionId, UserStoryDto userStoryDto) {
        UserStory storyToSave = userStoriesMapper.dtoToUserStory(userStoryDto);
        UserStory userStory = userStoryRepository.save(storyToSave);
        addStoryToSession(sessionId, userStory);
        return userStoriesMapper.userStoryToDto(userStory);
    }

    private void addStoryToSession(Long sessionId, UserStory userStory) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        List<UserStory> userStories = session.getUserStories();
        if (userStories.isEmpty()) {
            session.setUserStories(new ArrayList<>());
        }
        session.getUserStories().add(userStory);
        pokerSessionService.saveSession(session);
    }

    public UserStoryDto saveAndMapToDto(Long sessionId, Long storyId, UserStoryDto userStory) {
        validateSession(sessionId, storyId);
        UserStory storyToUpdate = findUserStory(storyId);
        storyToUpdate.setDescription(userStory.getDescription());
        storyToUpdate.setStatus(userStory.getStatus());
        return userStoriesMapper.userStoryToDto(userStoryRepository.save(storyToUpdate));
    }

    private void validateSession(Long sessionId, Long storyId) {
        boolean contain = pokerSessionService.getSessionById(sessionId).getUserStories()
                .stream()
                .map(UserStory::getId)
                .filter(id -> id.equals(storyId)).count() > 0;
        if (!contain) {
            throw new IllegalArgumentException("Story is not attached to session");
        }
    }

    public void startVotingStory(Long sessionId, Long storyId) {
        validateSession(sessionId, storyId);
        UserStory storyToUpdate = findUserStory(storyId);
        storyToUpdate.setStatus(UserStoryStatus.VOTING);
        userStoryRepository.save(storyToUpdate);
    }

    public UserStory deleteUserStory(Long sessionId, Long storyId) {
        validateSession(sessionId, storyId);
        UserStory storyToDelete = findUserStory(storyId);

        if (UserStoryStatus.PENDING != storyToDelete.getStatus()) {
            throw new IllegalArgumentException("UserStory is not in " + UserStoryStatus.PENDING + " status");
        }
        storyToDelete.setMembers(null);
        removeStoryFromSession(sessionId, storyToDelete);

        userStoryRepository.delete(storyToDelete);
        return storyToDelete;
    }

    private void removeStoryFromSession(Long sessionId, UserStory storyToDelete) {
        PokerSession sessionById = pokerSessionService.getSessionById(sessionId);
        sessionById.getUserStories().remove(storyToDelete);
        pokerSessionService.saveSession(sessionById);
        userStoryRepository.save(storyToDelete);
    }

    public UserStoryDto saveAndMapToDto(UserStory userStory) {
        return userStoriesMapper.userStoryToDto(userStoryRepository.save(userStory));
    }

    public UserStory findUserStory(Long storyId) {
        return userStoryRepository.findById(storyId).orElseThrow();
    }
}
