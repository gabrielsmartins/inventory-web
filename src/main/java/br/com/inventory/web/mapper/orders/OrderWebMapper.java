package br.com.inventory.web.mapper.orders;

import br.com.inventory.application.domain.orders.Order;
import br.com.inventory.application.domain.products.Product;
import br.com.inventory.application.ports.in.products.SearchProductUseCase;
import br.com.inventory.web.dto.request.orders.OrderRequestDto;
import br.com.inventory.web.dto.response.orders.OrderResponseDto;
import br.com.inventory.web.exception.InvalidOrderItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderWebMapper {

    private final SearchProductUseCase searchProductUseCase;


    public Order mapToDomain(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        List<OrderRequestDto.OrderItemRequestDto> items = orderRequestDto.getItems();


        for (OrderRequestDto.OrderItemRequestDto item : items) {
            Long id = item.getId();
            Optional<Product> optionalProduct = searchProductUseCase.findById(id);
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                BigDecimal amount = item.getAmount();
                var orderItem = new Order.OrderItem(order, product, amount);
                order.addItem(orderItem);
            }else{
                throw new InvalidOrderItemException("The item " + id + " is invalid");
            }

        }

        return order;
    }

    public OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(order.getId());
    }
}
