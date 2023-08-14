package com.hus23.assignment.socialmediaplatform.controller;
import com.hus23.assignment.socialmediaplatform.dto.AccountSearchDto;
import com.hus23.assignment.socialmediaplatform.dto.FollowRequestDto;
import com.hus23.assignment.socialmediaplatform.dto.UpdateUserRequestDto;
import com.hus23.assignment.socialmediaplatform.dto.UserDto;
import com.hus23.assignment.socialmediaplatform.service.UserService;
import com.hus23.assignment.socialmediaplatform.utils.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ControllerHelper controllerHelper;
    @PostMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult ){
        ResponseEntity<?> obj = controllerHelper.isRequestBodyValidated(bindingResult);
        if(Objects.nonNull(obj))
            return obj;
        return userService.addUser(userDto);
    }

    @PutMapping("/{userId}")
    public  ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto, @PathVariable("userId") String userId, BindingResult bindingResult){
        ResponseEntity<?> obj = controllerHelper.isRequestBodyValidated(bindingResult);
        if(Objects.nonNull(obj))
            return obj;
        return userService.updateUser(updateUserRequestDto, userId);
    }

    @DeleteMapping("/{userId}")
    public  ResponseEntity<?> deleteUser(@PathVariable("userId") String userId){
        return userService.deleteUser(userId);
    }

    @PostMapping("/follow/{currentUserId}")
    public ResponseEntity<?> followUser(@PathVariable("currentUserId") String currentUserId, @Valid @RequestBody FollowRequestDto followRequestDto, BindingResult bindingResult){
        ResponseEntity<?> obj = controllerHelper.isRequestBodyValidated(bindingResult);
        if(Objects.nonNull(obj))
            return obj;
        return userService.followUser(currentUserId, followRequestDto.getRequestUserId());
    }

    @DeleteMapping("/unfollow/{currentUserId}")
    public ResponseEntity<?> unFollowUser(@PathVariable("currentUserId") String currentUserId,@Valid @RequestBody FollowRequestDto followRequestDto, BindingResult bindingResult){
        ResponseEntity<?> obj = controllerHelper.isRequestBodyValidated(bindingResult);
        if(Objects.nonNull(obj))
            return obj;
        return userService.unFollowUser(currentUserId, followRequestDto.getRequestUserId());
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<?> getFollowing(@PathVariable("userId") String userId){
        return userService.getFollowing(userId);
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<?> getFollower(@PathVariable("userId") String userId){
        return userService.getFollower(userId);
    }

    @GetMapping("/search/{searchKey}")
    public ResponseEntity<?> searchAccount(@PathVariable("searchKey") String searchKey){
        return userService.searchAccount(searchKey);
    }

}
