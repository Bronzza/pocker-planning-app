package com.srepinet.pockerplanningapp.dto;

import com.srepinet.pockerplanningapp.entity.enums.UserStoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    private UserStoryStatus status = UserStoryStatus.PENDING;

}
