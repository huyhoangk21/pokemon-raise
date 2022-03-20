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

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  protected HttpResponse<Void> handleConflict(Exception ex, HttpServletRequest request) {

   return new HttpResponse<>(request.getRequestURI(),
                             HttpStatus.CONFLICT,
                             ex.getMessage(),
                             null);

  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PokeAPIException.class)
  protected HttpResponse<Void> handleInternalServerError(Exception ex, HttpServletRequest request) {

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.INTERNAL_SERVER_ERROR,
                              ex.getMessage(),
                              null);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  protected HttpResponse<Void> handleNotFound(Exception ex, HttpServletRequest request) {
    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.NOT_FOUND,
                              ex.getMessage(),
                              null);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(ForbiddenActionException.class)
  protected HttpResponse<Void> handleForbidden(Exception ex, HttpServletRequest request) {
    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.FORBIDDEN,
                              ex.getMessage(),
                              null);
  }
}
