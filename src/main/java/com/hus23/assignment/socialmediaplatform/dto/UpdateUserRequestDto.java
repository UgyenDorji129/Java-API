package com.hus23.assignment.socialmediaplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {

    @Size(min = 3,message = "First name can't be Empty")
    private String firstName;

    @Size(min = 3,message = "Last name can't be Empty")
    private String lastName;

    @Size(min = 1,message = "Bio can't be Empty")
    private String userBio;

}
