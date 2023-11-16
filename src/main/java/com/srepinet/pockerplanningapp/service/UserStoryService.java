package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import com.srepinet.pockerplanningapp.mapper.UserStoriesMapper;
import com.srepinet.pockerplanningapp.repository.UserStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final PokerSessionService pokerSessionService;
    private final UserStoriesMapper userStoriesMapper;

    public List<UserStoryDto> findAll(Long sessionId) {
        List<UserStoryDto> userStories = userStoryRepository.findBySessionId(sessionId).stream()
                .map(userStoriesMapper::userStoryToDto)
                .toList();
        if (userStories.isEmpty()) {
            throw new NoSuchElementException("No user stories found");
        }
        return userStories;
    }

    public UserStory findUserStory(Long sessionId, Long id) {
        return userStoryRepository.findBySessionIdAndId(sessionId, id).orElseThrow();
    }

    public UserStoryDto createStory(Long sessionId, UserStoryDto userStoryDto) {
        PokerSession session = pokerSessionService.getSessionById(sessionId);
        UserStory storyToSave = userStoriesMapper.dtoToUserStory(userStoryDto);
        storyToSave.setSession(session);
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
}
