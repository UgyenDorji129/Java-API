package com.hus23.assignment.socialmediaplatform;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController {


    @GetMapping("/hello")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        return new ResponseEntity<String>("Hello", HttpStatus.valueOf(200));
    }

}
