package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.VoteDto;
import com.srepinet.pockerplanningapp.entity.Vote;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VoteMapper {
    VoteDto voteToDto(Vote vote);

    Vote dtoToVote(VoteDto voteDto);
}
