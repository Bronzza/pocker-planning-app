package com.srepinet.pockerplanningapp.entity;

import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_stories_members",
            joinColumns = @JoinColumn(
                    name = "user_story_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "id"
            )
    )
    private List<Member> members;

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL)
    private Set<Vote> votes;

}
