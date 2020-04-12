package br.com.inventory.web.controller.orders;

import br.com.inventory.application.domain.orders.Order;
import br.com.inventory.application.ports.in.orders.SubmitOrderUseCase;
import br.com.inventory.web.dto.request.orders.OrderRequestDto;
import br.com.inventory.web.dto.response.orders.OrderResponseDto;
import br.com.inventory.web.mapper.orders.OrderWebMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Api(value = "Order Controller", description = "Order Controller", tags = {"Order"})
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final SubmitOrderUseCase submitOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    @ApiOperation(value = "Submit Order", tags = {"Order"})
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted Order"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping()
    public ResponseEntity<?> submit(@RequestHeader HttpHeaders httpHeaders,
                                    @RequestBody @Valid OrderRequestDto orderRequestDto){
        log.info("New Request ... {}, {}", keyValue("Headers",httpHeaders),  keyValue("Body",orderRequestDto));
        log.info("Mapping Request...");
        Order order = orderWebMapper.mapToDomain(orderRequestDto);
        log.info("Request was mapped successfully ... {}", order);
        log.info("Creating Product ...");
        Order submittedOrder = submitOrderUseCase.submit(order);
        log.info("Product was created successfully ...");
        log.info("Mapping Response ...");
        OrderResponseDto orderResponseDto = orderWebMapper.mapToDto(submittedOrder);
        log.info("Response was mapped successfully ... {}", orderResponseDto);
        return new ResponseEntity<>(orderResponseDto,HttpStatus.ACCEPTED);
    }
}
