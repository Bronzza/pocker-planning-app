package com.srepinet.pockerplanningapp.controller;

import com.srepinet.pockerplanningapp.dto.VoteDto;
import com.srepinet.pockerplanningapp.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions/{sessionId}/votes")
public class VoteController {
    private final VoteService voteService;

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllVotes(@PathVariable Long sessionId) {
        return ResponseEntity.ok(voteService.findAll(sessionId));
    }

    @PostMapping
    public ResponseEntity<VoteDto> vote(@PathVariable Long sessionId, @Valid @RequestBody VoteDto voteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.applyVote(sessionId, voteDto));
    }

    @PatchMapping
    public ResponseEntity<List<VoteDto>> endVoting(@PathVariable Long sessionId, @RequestBody VoteDto voteDto) {
        return ResponseEntity.ok(voteService.endVoting(sessionId, voteDto));
    }
}
