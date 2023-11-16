package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.PokerSessionDto;
import com.srepinet.pockerplanningapp.entity.PokerSession;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PokerSessionMapper {

    @Mapping(target = "sessionId", source = "id")
    PokerSessionDto pokerSessionToDto(PokerSession pokerSession);

    @Mapping(target = "id", source = "sessionId")
    PokerSession dtoToPokerSession(PokerSessionDto pokerSessionDto);
}
