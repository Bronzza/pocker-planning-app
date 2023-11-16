package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.VoteDto;
import com.srepinet.pockerplanningapp.entity.Member;
import com.srepinet.pockerplanningapp.entity.UserStory;
import com.srepinet.pockerplanningapp.entity.Vote;
import com.srepinet.pockerplanningapp.service.MemberService;
import com.srepinet.pockerplanningapp.service.UserStoryService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public abstract class VoteMapper {

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected UserStoryService userStoryService;

    @Mappings({
            @Mapping(target = "memberId", expression = "java(vote.getMember().getId())"),
            @Mapping(target = "userStoryId", expression = "java(vote.getUserStory().getId())")
    })
    public abstract VoteDto voteToDto(Vote vote);

    @Mappings({
            @Mapping(target = "member", expression = "java(memberService.findMember(voteDto.getMemberId()))"),
            @Mapping(target = "userStory", expression = "java(userStoryService.findUserStory(voteDto.getUserStoryId()))")
    })
    public abstract Vote dtoToVote(VoteDto voteDto);

    @Named("memberToId")
    public static Long memberToId(Member member) {
        return member.getId();
    }

    @Named("storyToId")
    public static Long storyToId(UserStory userStory) {
        return userStory.getId();
    }
}
