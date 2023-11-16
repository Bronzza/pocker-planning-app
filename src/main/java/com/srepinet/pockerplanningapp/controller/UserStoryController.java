package com.srepinet.pockerplanningapp.controller;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.service.UserStoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions/{sessionId}/stories")
public class UserStoryController {
    private final UserStoryService userStoryService;

    @GetMapping
    public ResponseEntity<List<UserStoryDto>> getAllStories(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(userStoryService.findAll(sessionId));
    }

    @PostMapping
    public ResponseEntity<UserStoryDto> createStory(@PathVariable Long sessionId,
                                                    @Valid @RequestBody UserStoryDto userStory) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userStoryService.createStory(sessionId, userStory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryDto> updateStory(@PathVariable Long sessionId, @PathVariable Long id,
                                                    @Valid @RequestBody UserStoryDto userStoryDto) {
        return ResponseEntity.ok().body(userStoryService.updateUserStory(sessionId, id, userStoryDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity startVotingStory(@PathVariable Long sessionId, @PathVariable Long id) {
        userStoryService.startVotingStory(sessionId, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity enterUserStoryByMember(@PathVariable Long sessionId, @PathVariable Long storyId, @RequestBody
                                                 MemberDto memberDto) {
        userStoryService.enterStory(sessionId, storyId, memberDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStory(@PathVariable Long sessionId, @PathVariable Long id) {
        userStoryService.deleteUserStory(sessionId, id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
