package com.srepinet.pockerplanningapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="story_id")
    private UserStory userStory;

    private Integer voteResult;

    @JsonBackReference
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id", nullable = false)
    private PokerSession session;

}
