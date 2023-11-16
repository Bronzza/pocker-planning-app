package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.entity.UserStory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserStoriesMapper {

    UserStoryDto userStoryToDto(UserStory userStory);

    UserStory dtoToUserStory(UserStoryDto userStoryDto);
}
