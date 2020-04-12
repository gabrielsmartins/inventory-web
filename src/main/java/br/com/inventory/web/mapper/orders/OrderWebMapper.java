package br.com.inventory.web.mapper.orders;

import br.com.inventory.application.domain.orders.Order;
import br.com.inventory.web.dto.request.orders.OrderRequestDto;
import br.com.inventory.web.dto.response.orders.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderWebMapper {


    public Order mapToDomain(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        return order;
    }

    public OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(order.getId());
    }
}
