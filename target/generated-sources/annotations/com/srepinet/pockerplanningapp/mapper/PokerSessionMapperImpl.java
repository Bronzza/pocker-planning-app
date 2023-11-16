package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.PokerSessionDto;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-16T18:47:16+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class PokerSessionMapperImpl implements PokerSessionMapper {

    @Override
    public PokerSessionDto pokerSessionToDto(PokerSession pokerSession) {
        if ( pokerSession == null ) {
            return null;
        }

        PokerSessionDto.PokerSessionDtoBuilder pokerSessionDto = PokerSessionDto.builder();

        pokerSessionDto.sessionId( pokerSession.getId() );
        pokerSessionDto.title( pokerSession.getTitle() );

        return pokerSessionDto.build();
    }

    @Override
    public PokerSession dtoToPokerSession(PokerSessionDto pokerSessionDto) {
        if ( pokerSessionDto == null ) {
            return null;
        }

        PokerSession.PokerSessionBuilder pokerSession = PokerSession.builder();

        pokerSession.id( pokerSessionDto.getSessionId() );
        pokerSession.title( pokerSessionDto.getTitle() );

        return pokerSession.build();
    }
}
