package com.srepinet.pockerplanningapp.controller;

import com.srepinet.pockerplanningapp.dto.PokerSessionDto;
import com.srepinet.pockerplanningapp.service.PokerSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class PokerSessionController {
    private final PokerSessionService pokerSessionService;

    @GetMapping
    public ResponseEntity<List<PokerSessionDto>> getAllSessions() {
        List<PokerSessionDto> sessions = pokerSessionService.findAll();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokerSessionDto> getSession(@PathVariable Long id) {
        PokerSessionDto session = pokerSessionService.getSessionDtoById(id);
        return ResponseEntity.ok(session);
    }

    @PostMapping
    public ResponseEntity<PokerSessionDto> createSession(@Valid @RequestBody PokerSessionDto session) {
        PokerSessionDto created = pokerSessionService.create(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSession(@PathVariable Long id) {
        pokerSessionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
