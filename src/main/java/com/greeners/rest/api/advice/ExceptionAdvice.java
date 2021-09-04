package com.greeners.rest.api.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greeners.rest.api.advice.exception.CustomCommunicationException;
import com.greeners.rest.api.advice.exception.CustomForbiddenWordException;
import com.greeners.rest.api.advice.exception.CustomResourceNotExistException;
import com.greeners.rest.api.model.response.CommonResult;
import com.greeners.rest.api.service.common.ResponseService;

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
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.message") + "(" + e.getMessage() + ")");
    }

    @ExceptionHandler(CustomCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult communicationException(HttpServletRequest request, CustomCommunicationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.message"));
    }

    @ExceptionHandler(CustomResourceNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException(HttpServletRequest request, CustomResourceNotExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.message"));
    }

    @ExceptionHandler(CustomForbiddenWordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult forbiddenWordException(HttpServletRequest request, CustomForbiddenWordException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("forbiddenWord.code")), getMessage("forbiddenWord.message", new Object[]{e.getMessage()}));
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