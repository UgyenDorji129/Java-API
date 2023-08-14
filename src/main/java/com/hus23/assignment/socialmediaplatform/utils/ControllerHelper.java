package com.hus23.assignment.socialmediaplatform.utils;


import com.hus23.assignment.socialmediaplatform.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
public class ControllerHelper {
    public ResponseEntity<?> isRequestBodyValidated(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return getResponseBody(HttpStatus.BAD_REQUEST, errors);
        }
        return null;
    }
    public ResponseEntity<?> getResponseBody(HttpStatus status, Object body){
        return ResponseEntity.status(status).body(body);
    }
}
