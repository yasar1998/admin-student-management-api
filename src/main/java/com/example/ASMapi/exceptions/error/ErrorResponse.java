package com.example.ASMapi.exceptions.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonPropertyOrder({"timestamp", "status", "error", "path", "validation_errors"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonProperty("timestamp")
    String timeStamp;
    Integer status;
    String error;
    String path;

    @JsonProperty("validation_errors")
    Map<String, String> validationErrors;

    public ErrorResponse(String timeStamp, Integer status, String error, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
