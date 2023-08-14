package com.hus23.assignment.socialmediaplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FollowRequestDto {
    @NotNull(message = "Request User ID can not be null")
    @NotBlank(message = "Request User ID can not be Blank")
    private String requestUserId;
}
