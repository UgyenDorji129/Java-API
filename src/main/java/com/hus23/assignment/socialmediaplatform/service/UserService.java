package com.hus23.assignment.socialmediaplatform.service;

import com.hus23.assignment.socialmediaplatform.dto.AccountSearchDto;
import com.hus23.assignment.socialmediaplatform.dto.ResponseDto;
import com.hus23.assignment.socialmediaplatform.dto.UpdateUserRequestDto;
import com.hus23.assignment.socialmediaplatform.dto.UserDto;
import com.hus23.assignment.socialmediaplatform.entity.User;
import com.hus23.assignment.socialmediaplatform.repository.UserRepo;
import com.hus23.assignment.socialmediaplatform.utils.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ControllerHelper controllerHelper;
    public ResponseEntity<?> addUser(UserDto userDto){

        Optional<User> userByName = userRepo.findByUserName(userDto.getUserName());

        if(userByName.isPresent()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName already Exist!")
                    .data(userDto)
                    .status(HttpStatus.NOT_ACCEPTABLE.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.NOT_ACCEPTABLE, responseBody);
        }
        User user = User.builder()
                .userBio(userDto.getUserBio())
                .userName(userDto.getUserName())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .password(userDto.getPassword())
        .build();
        userRepo.save(user);
        ResponseDto responseBody = ResponseDto.builder()
                .message("User created Successful")
                .data(userDto)
                .status(HttpStatus.CREATED.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.CREATED, responseBody);
    }

    public  ResponseEntity<?> updateUser(UpdateUserRequestDto updateUserRequestDto, String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);

        if(userByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        User user = userByName.get();
        if(Objects.nonNull(updateUserRequestDto.getFirstName())) {
            user.setFirstName(updateUserRequestDto.getFirstName());
        }
        if(Objects.nonNull(updateUserRequestDto.getLastName())) {
            user.setFirstName(updateUserRequestDto.getLastName());
        }
        if(Objects.nonNull(updateUserRequestDto.getUserBio())) {
            user.setFirstName(updateUserRequestDto.getUserBio());
        }

        userRepo.save(user);
        ResponseDto responseBody = ResponseDto.builder()
                .message("User Detail Updated Successful")
                .data(userId)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);

    }

    public  ResponseEntity<?> deleteUser(String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);

        if(userByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        User user = userByName.get();
        userRepo.delete(user);
        ResponseDto responseBody = ResponseDto.builder()
                .message("User Deleted Successful")
                .data(userId)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);

    }

    public ResponseEntity<?> followUser(String currentUserId, String requestedUserId){
        Optional<User> requestedUserByName = userRepo.findByUserName(requestedUserId);
        Optional<User> currentUserByName = userRepo.findByUserName(currentUserId);
        if(requestedUserByName.isEmpty() || currentUserByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(requestedUserId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();

            return controllerHelper.getResponseBody(HttpStatus.NO_CONTENT, responseBody);
        }
        User requestedUser = requestedUserByName.get();
        User currentUser = currentUserByName.get();

        currentUser.getFollowing().add(requestedUser);
        requestedUser.getFollowers().add(currentUser);
        userRepo.save(currentUser);
        userRepo.save(requestedUser);

        ResponseDto responseBody = ResponseDto.builder()
                .message("User followed Successfully!")
                .data(currentUserId)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }

    public ResponseEntity<?> unFollowUser(String currentUserId, String requestedUserId){
        Optional<User> requestedUserByName = userRepo.findByUserName(requestedUserId);
        Optional<User> currentUserByName = userRepo.findByUserName(currentUserId);

        if(requestedUserByName.isEmpty() || currentUserByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(requestedUserId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.NO_CONTENT, responseBody);
        }
        User requestedUser = requestedUserByName.get();
        User currentUser = currentUserByName.get();
            boolean isSuccessCurrent = currentUser.getFollowers().remove(requestedUser);
            boolean isSuccessRequest = requestedUser.getFollowing().remove(currentUser);
            userRepo.save(currentUser);
            userRepo.save(requestedUser);

            if(isSuccessCurrent && isSuccessRequest){
                ResponseDto responseBody = ResponseDto.builder()
                        .message("User Unfollowed Successfully!")
                        .data(currentUserId)
                        .status(HttpStatus.OK.toString())
                        .build();
                return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
            }
        ResponseDto responseBody = ResponseDto.builder()
                .message("This two User don't follow each Other!")
                .data(currentUser.getFollowers().contains(requestedUser))
                .status(HttpStatus.BAD_REQUEST.toString())
                .build();

        return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);

    }

    public ResponseEntity<?> getFollowing(String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);
        if(userByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        Set<User> following = userByName.get().getFollowing();
        if(following.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User does not follow anyone!")
                    .data(null)
                    .status(HttpStatus.OK.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
        }
        ResponseDto responseBody = ResponseDto.builder()
                .message("User's Following retrieved Successfully!")
                .data(following)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }

    public ResponseEntity<?> getFollower(String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);

        if(userByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        Set<User> followers = userByName.get().getFollowers();
        if(followers.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User does not have any follower!")
                    .data(null)
                    .status(HttpStatus.OK.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
        }
        ResponseDto responseBody = ResponseDto.builder()
                .message("User's Follower retrieved Successfully!")
                .data(followers)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }
    public  ResponseEntity<?> searchAccount(String searchKey){
        List<User>  user = userRepo.findByUserNameIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(
                searchKey, searchKey,searchKey
        );
        if(user.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("No User Found!")
                    .data(searchKey)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }

        ResponseDto responseBody = ResponseDto.builder()
                .message("Result according to the Search Inputs")
                .data(user)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);

    }
}
