package com.hoangle.pokemon.exception;

import com.hoangle.pokemon.dto.response.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {

    String errorMessage = "";

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errorMessage = error.getDefaultMessage();
    }

    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errorMessage= error.getDefaultMessage();
    }

    HttpResponse<Void> httpResponse = new HttpResponse<>(request.getDescription(false),
                                                         HttpStatus.BAD_REQUEST,
                                                         errorMessage,
                                                         null);

    return handleExceptionInternal(ex, httpResponse, headers, httpResponse.getStatus(), request);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UserAlreadyExistsException.class)
  protected HttpResponse<Void> handleUsernameAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {

   return new HttpResponse<>(request.getRequestURI(),
                             HttpStatus.BAD_REQUEST,
                             ex.getMessage(),
                             null);

  }
}
