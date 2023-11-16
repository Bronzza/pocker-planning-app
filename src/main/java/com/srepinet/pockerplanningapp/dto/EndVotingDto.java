package com.srepinet.pockerplanningapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndVotingDto {
    @NotNull
    private Long userStoryId;
}
