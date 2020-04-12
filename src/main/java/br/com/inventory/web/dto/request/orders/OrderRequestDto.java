package br.com.inventory.web.dto.request.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"items"})
@ApiModel(value = "Order Request")
public class OrderRequestDto {

    @JsonProperty("date_time")
    @ApiModelProperty(value = "Datetime", example = "2020-01-01 14:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @JsonProperty("items")
    @ApiModelProperty(value = "Items")
    private List<OrderItemRequestDto> items = new LinkedList<>();

    @Getter
    @Setter
    @ToString
    @ApiModel(value = "Order Item Request")
    public static class OrderItemRequestDto{

        @JsonProperty("product_id")
        @ApiModelProperty(value = "Product ID", example = "1")
        private Long id;

        @JsonProperty("amount")
        @ApiModelProperty(value = "Amount", example = "1500.50")
        private BigDecimal amount;
    }


}
