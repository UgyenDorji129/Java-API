package com.hus23.assignment.socialmediaplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Time;

@AllArgsConstructor
@Data
@Builder
public class PostDto {

    @NotNull(message = "Post Title can not be null")
    @NotBlank(message = "Post Title can not be Blank")
    private String postTitle;

    @NotNull(message = "Post location can not be null")
    @NotBlank(message = "Post location can not be Blank")
    private String postLocation;

}
