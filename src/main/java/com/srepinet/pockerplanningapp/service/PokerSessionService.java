package com.srepinet.pockerplanningapp.service;

import com.srepinet.pockerplanningapp.dto.PokerSessionDto;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import com.srepinet.pockerplanningapp.mapper.PokerSessionMapper;
import com.srepinet.pockerplanningapp.repository.PokerSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokerSessionService {

    private final PokerSessionRepository repository;
    private final PokerSessionMapper pokerSessionMapper;

    public List<PokerSessionDto> findAll() {
        return repository.findAll()
                .stream()
                .map(pokerSessionMapper::pokerSessionToDto)
                .toList();
    }

    public PokerSessionDto getSessionDtoById(Long sessionId) {
        return pokerSessionMapper.pokerSessionToDto(getSessionById(sessionId));
    }

    public PokerSession getSessionById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public PokerSessionDto create(PokerSessionDto sessionDto) {
        return pokerSessionMapper.pokerSessionToDto(repository.save(pokerSessionMapper.dtoToPokerSession(sessionDto)));
    }

    public void delete(Long sessionId) {
        PokerSession session = repository.findById(sessionId).orElseThrow();
        repository.delete(session);
    }

    public void saveSession(PokerSession session) {
        repository.save(session);
    }
}
