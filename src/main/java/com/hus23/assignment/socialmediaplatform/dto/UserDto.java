package com.hus23.assignment.socialmediaplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@Builder

public class UserDto {
    @NotNull(message = "User Name can not be null")
    @NotBlank(message = "User Name can not be Blank")
    private String userName;

    @NotNull(message = "First Name can not be null")
    @NotBlank(message = "First Name can not be Blank")
    private String firstName;

    @NotNull(message = "Last Name can not be null")
    @NotBlank(message = "Last Name can not be Blank")
    private String lastName;
    private String userBio;

    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be Blank")
    @Size(min = 8, message = "Password should be of more than eight characters")
    private String password;
}
