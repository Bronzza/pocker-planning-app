package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.MemberDto;
import com.srepinet.pockerplanningapp.entity.Member;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MemberMapper {

    MemberDto memberToDto(Member member);

    Member dtoToMember(MemberDto memberDto);
}
