package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.VoteDto;
import com.srepinet.pockerplanningapp.entity.Vote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-16T18:47:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class VoteMapperImpl implements VoteMapper {

    @Override
    public VoteDto voteToDto(Vote vote) {
        if ( vote == null ) {
            return null;
        }

        VoteDto voteDto = new VoteDto();

        voteDto.setId( vote.getId() );
        voteDto.setMemberId( vote.getMemberId() );
        voteDto.setUserStoryId( vote.getUserStoryId() );
        voteDto.setVoteResult( vote.getVoteResult() );

        return voteDto;
    }

    @Override
    public Vote dtoToVote(VoteDto voteDto) {
        if ( voteDto == null ) {
            return null;
        }

        Vote vote = new Vote();

        vote.setId( voteDto.getId() );
        vote.setMemberId( voteDto.getMemberId() );
        vote.setUserStoryId( voteDto.getUserStoryId() );
        vote.setVoteResult( voteDto.getVoteResult() );

        return vote;
    }
}
