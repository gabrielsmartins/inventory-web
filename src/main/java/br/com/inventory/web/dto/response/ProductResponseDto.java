package br.com.inventory.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {

    @JsonProperty("id")
    private final Long id;
}
