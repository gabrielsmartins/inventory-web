package br.com.inventory.web.dto.response.products;


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
@ApiModel(value = "Product Response")
public class ProductResponseDto {

    @JsonProperty("product_id")
    @ApiModelProperty(value = "Product ID")
    private  Long id;

    @JsonProperty("description")
    @ApiModelProperty(value = "Description")
    private String description;

    @JsonProperty("type")
    @ApiModelProperty(value = "Type")
    private String type;

    @JsonProperty("amount")
    @ApiModelProperty(value = "Amount")
    private BigDecimal amount;
}
