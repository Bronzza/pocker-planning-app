package com.srepinet.pockerplanningapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private UserStoryStatus status = UserStoryStatus.PENDING;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private PokerSession session;

}
