package com.hus23.assignment.socialmediaplatform.service;

import com.hus23.assignment.socialmediaplatform.dto.PostDto;
import com.hus23.assignment.socialmediaplatform.dto.ResponseDto;
import com.hus23.assignment.socialmediaplatform.entity.Post;
import com.hus23.assignment.socialmediaplatform.entity.User;
import com.hus23.assignment.socialmediaplatform.repository.PostRepo;
import com.hus23.assignment.socialmediaplatform.repository.UserRepo;
import com.hus23.assignment.socialmediaplatform.utils.ControllerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private  final UserRepo userRepo;
    private  final ControllerHelper controllerHelper;
    private  final PostRepo postRepo;

    public ResponseEntity<?> addPost(PostDto postDto, String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);
        if(userByName.isPresent()){

            Post post = Post.builder()
                    .postTitle(postDto.getPostTitle())
                    .lastChangedTime(System.currentTimeMillis()/1000)
                    .postLocation(postDto.getPostLocation())
                    .user(userByName.get())
                    .build();
            postRepo.save(post);
            ResponseDto responseBody = ResponseDto.builder()
                    .message("Post created Successful")
                    .data(postDto)
                    .status(HttpStatus.CREATED.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.CREATED, responseBody);
        }
        ResponseDto responseBody = ResponseDto.builder()
                .message("Could not make the post!")
                .data(postDto)
                .status(HttpStatus.NOT_ACCEPTABLE.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.NOT_ACCEPTABLE, responseBody);
    }

    public  ResponseEntity<?> searchPost(String postLocation){

        List<Post> postByLocation =  postRepo.findByPostLocationIgnoreCaseContaining(postLocation);
        if(postByLocation.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("Post with this Location name Not Found!")
                    .data(postLocation)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }

        ResponseDto responseBody = ResponseDto.builder()
                .message("Result according to the Search Inputs")
                .data(postByLocation)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }

    public ResponseEntity<?> getUserPost(String userId){
        Optional<User> userByName = userRepo.findByUserName(userId);

        if(userByName.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("User with this UserName Not Found!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        List<Post> userPost = postRepo.findAllByUserOrderByLastChangedTime(userByName.get());
        if(userPost.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("No Post Found by the user!")
                    .data(userId)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        ResponseDto responseBody = ResponseDto.builder()
                .message("List of post by the User!")
                .data(userPost)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }
    @Transactional
    public ResponseEntity<?> deleteAllPost(String userId){
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
        postRepo.deleteByUser(user);
        ResponseDto responseBody = ResponseDto.builder()
                .message("All post Deleted!")
                .data(userId)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);
    }

    @Transactional
    public ResponseEntity<?> deletePostByPostId(Integer postId){
        Optional<Post> postById = postRepo.findByPostId(postId);
        if(postById.isEmpty()){
            ResponseDto responseBody = ResponseDto.builder()
                    .message("Post with this ID Not Found!")
                    .data(postById)
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .build();
            return controllerHelper.getResponseBody(HttpStatus.BAD_REQUEST, responseBody);
        }
        postRepo.deleteById(postId);
        ResponseDto responseBody = ResponseDto.builder()
                .message("A post has been Deleted!")
                .data(postId)
                .status(HttpStatus.OK.toString())
                .build();
        return controllerHelper.getResponseBody(HttpStatus.OK, responseBody);

    }



}
