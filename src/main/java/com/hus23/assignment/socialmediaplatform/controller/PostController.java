package com.hus23.assignment.socialmediaplatform.controller;

import com.hus23.assignment.socialmediaplatform.dto.AccountSearchDto;
import com.hus23.assignment.socialmediaplatform.dto.PostDto;
import com.hus23.assignment.socialmediaplatform.service.PostService;
import com.hus23.assignment.socialmediaplatform.utils.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final ControllerHelper controllerHelper;
    private  final PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult, @PathVariable("userId") String userId){
        ResponseEntity<?> obj = controllerHelper.isRequestBodyValidated(bindingResult);
        if(Objects.nonNull(obj))
            return obj;
        return postService.addPost(postDto, userId);
    }

    @GetMapping("/search/{postLocation}")
    public  ResponseEntity<?> searchPost(@PathVariable("postLocation") String postLocation){
        return  postService.searchPost(postLocation);
    }

    @GetMapping("/{userId}")
    public  ResponseEntity<?> getUserPost(@PathVariable("userId") String userId){
        return postService.getUserPost(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAllPost(@PathVariable("userId") String userId){
        return postService.deleteAllPost(userId);
    }

    @DeleteMapping("delete/{postId}")
    public ResponseEntity<?> deleteByPostId(@PathVariable("postId") Integer postId){
        return postService.deletePostByPostId(postId);
    }


}
