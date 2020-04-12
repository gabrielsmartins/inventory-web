package br.com.inventory.web.dto.request.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@ApiModel(value = "Product Request")
public class ProductRequestDto {

    @JsonProperty("description")
    @ApiModelProperty(value = "Description", example = "Smartphone")
    private String description;

    @JsonProperty("type")
    @ApiModelProperty(value = "Type", example = "ELECTRONICS")
    private String type;

    @JsonProperty("amount")
    @ApiModelProperty(value = "Amount", example = "1500.50")
    private BigDecimal amount;
}
