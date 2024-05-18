package org.aovsa.tinyurl.Controllers.Advice;

import org.aovsa.tinyurl.Exceptions.ErrorMessage;
import org.aovsa.tinyurl.Exceptions.MetricNotWhitelistedException;
import org.aovsa.tinyurl.Exceptions.TinyURLNotFoundException;
import org.aovsa.tinyurl.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {TinyURLNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorMessage> handleTinyURLNotFoundException(TinyURLNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,new Date(), e.getMessage());
        return new ApiResponse<>(errorMessage, "Tiny URL not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MetricNotWhitelistedException.class})
    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
    public ApiResponse<ErrorMessage> handleMetricNotWhitelistedException(MetricNotWhitelistedException e) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FAILED_DEPENDENCY, new Date(), e.getMessage());
        return new ApiResponse<>(errorMessage, "Tiny URL not found", HttpStatus.FAILED_DEPENDENCY);
    }
}
