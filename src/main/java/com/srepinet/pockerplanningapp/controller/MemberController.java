package com.srepinet.pockerplanningapp.controller;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.service.MemberService;
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
@RequestMapping("/sessions/{sessionId}/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(@PathVariable Long sessionId) {
        List<MemberDto> members = memberService.findAll(sessionId);
        return members.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(members);
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@PathVariable Long sessionId, @Valid @RequestBody MemberDto memberDto) {
        MemberDto joined = memberService.joinSession(sessionId, memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(joined);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable Long sessionId, @PathVariable Long id) {
        memberService.logout(sessionId, id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
