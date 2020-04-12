package br.com.inventory.web.dto.response.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Order Response")
public class OrderResponseDto {

    @JsonProperty("order_id")
    @ApiModelProperty(value = "Order ID", example = "1")
    private Long id;

    public OrderResponseDto(Long id) {
        this.id = id;
    }
}
