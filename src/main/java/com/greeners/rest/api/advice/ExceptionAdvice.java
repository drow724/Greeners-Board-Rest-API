package com.greeners.rest.api.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greeners.rest.api.advice.exception.CustomAuthenticationEntryPointException;
import com.greeners.rest.api.advice.exception.CustomCommunicationException;
import com.greeners.rest.api.advice.exception.CustomEmailSigninFailedException;
import com.greeners.rest.api.advice.exception.CustomUserExistException;
import com.greeners.rest.api.advice.exception.CustomUserNotFoundException;
import com.greeners.rest.api.model.response.CommonResult;
import com.greeners.rest.api.service.ResponseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CustomUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomUserNotFoundException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CustomEmailSigninFailedException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        protected CommonResult emailSigninFailed(HttpServletRequest request, CustomEmailSigninFailedException e) {
            return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }
    
    @ExceptionHandler(CustomAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CustomAuthenticationEntryPointException e) {
            return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
            return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }
    
    @ExceptionHandler(CustomCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomCommunicationException e) {
            return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
        }
    
    @ExceptionHandler(CustomUserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public CommonResult communicationException(HttpServletRequest request, CustomUserExistException e) {
            return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
    }
    
    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}