package com.srepinet.pockerplanningapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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

    @ManyToMany (cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "user_stories_members",
            joinColumns = @JoinColumn(
                    name = "user_story_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "id"
            )
    )
    private Set<Member> members;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "user_stories_sessions",
            joinColumns = @JoinColumn(name = "user_story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"))
    private List<PokerSession> sessions;

}
