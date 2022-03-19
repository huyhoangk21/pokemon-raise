package com.hoangle.pokemon.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class HttpResponse<T> {

  private String path;

  private HttpStatus status;

  private String message;

  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime timestamp;

  private T data;

  public HttpResponse(String path, HttpStatus status, String message, T data) {
    this.path = path;
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
    this.data = data;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
