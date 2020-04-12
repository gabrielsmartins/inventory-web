package br.com.inventory.web.dto.response.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Error Message Response")
@Getter
@Setter
public class ErrorMessageResponseDto {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("message")
    private String message;

    @JsonProperty("path")
    private String path;

    @JsonProperty("stackTrace")
    private String stackTrace;
}
