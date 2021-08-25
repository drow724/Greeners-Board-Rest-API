package com.greeners.rest.api.controller.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greeners.rest.api.advice.exception.CustomAuthenticationEntryPointException;
import com.greeners.rest.api.model.response.CommonResult;

import lombok.RequiredArgsConstructor;

// import 생략

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CustomAuthenticationEntryPointException();
    }
    
    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
            throw new AccessDeniedException("");
    }
    
}