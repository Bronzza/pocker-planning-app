package com.srepinet.pockerplanningapp.mapper;

import com.srepinet.pockerplanningapp.dto.UserStoryDto;
import com.srepinet.pockerplanningapp.entity.UserStory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-16T18:47:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class UserStoriesMapperImpl implements UserStoriesMapper {

    @Override
    public UserStoryDto userStoryToDto(UserStory userStory) {
        if ( userStory == null ) {
            return null;
        }

        UserStoryDto userStoryDto = new UserStoryDto();

        userStoryDto.setId( userStory.getId() );
        userStoryDto.setDescription( userStory.getDescription() );
        userStoryDto.setStatus( userStory.getStatus() );

        return userStoryDto;
    }

    @Override
    public UserStory dtoToUserStory(UserStoryDto userStoryDto) {
        if ( userStoryDto == null ) {
            return null;
        }

        UserStory userStory = new UserStory();

        userStory.setId( userStoryDto.getId() );
        userStory.setDescription( userStoryDto.getDescription() );
        userStory.setStatus( userStoryDto.getStatus() );

        return userStory;
    }
}
