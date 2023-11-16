package com.srepinet.pockerplanningapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long userStoryId;

    @NotNull
    private Integer voteResult;


    private Boolean isLastVote;
}
