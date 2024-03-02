package com.study.footprint.controller.common.exception;

import com.study.footprint.common.exception.CommonConflictException;
import com.study.footprint.common.response.CommonResult;
import com.study.footprint.service.common.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ExceptionAdviceController {

    private final ResponseService responseService;

    public ExceptionAdviceController(ResponseService responseService) {
        this.responseService = responseService;
    }

    /**
     * CommonConflictException 처리
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CommonConflictException.class)
    public CommonResult conflictException(CommonConflictException e) {
        return responseService.getFailResult(e.getMessage());
    }

    /**
     * MethodArgumentNotValidException 처리 (DTO validation)
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<CommonResult> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<CommonResult> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error)-> {
            String exception = error.getDefaultMessage();
            errors.add(responseService.getFailResult(exception));
        });

        return errors;
    }
}
