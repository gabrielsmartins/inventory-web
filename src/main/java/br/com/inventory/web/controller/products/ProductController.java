package br.com.inventory.web.controller.products;


import br.com.inventory.application.domain.products.Product;
import br.com.inventory.application.ports.in.products.CreateProductUseCase;
import br.com.inventory.application.ports.in.products.SearchProductUseCase;
import br.com.inventory.web.dto.request.products.ProductRequestDto;
import br.com.inventory.web.dto.response.products.ProductResponseDto;
import br.com.inventory.web.mapper.products.ProductWebMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Api(value = "Product Controller", description = "Product Controller",  tags = {"Product"})
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final ProductWebMapper productWebMapper;


    @ApiOperation(value = "Create Product", tags = {"Product"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created Product"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping()
    public ResponseEntity<?> create(@RequestHeader HttpHeaders httpHeaders, @RequestBody  @Valid ProductRequestDto productRequestDto, UriComponentsBuilder uriBuilder){
        log.info("New Request ... {}, {}", keyValue("Headers",httpHeaders),  keyValue("Body",productRequestDto));
        log.info("Mapping Request...");
        Product product = productWebMapper.mapToDomain(productRequestDto);
        log.info("Request was mapped successfully ... {}", product);
        log.info("Creating Product ...");
        Product createdProduct = createProductUseCase.create(product);
        log.info("Product was created successfully ...");
        log.info("Mapping Response ...");
        ProductResponseDto productResponseDto = productWebMapper.mapToDto(createdProduct);
        log.info("Response was mapped successfully ... {}", productResponseDto);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

    @ApiOperation(value = "Search Product", tags = {"Product"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Founded Product"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id, @RequestHeader HttpHeaders httpHeaders){
        log.info("New Request ... {}, {}", keyValue("Headers",httpHeaders), keyValue("PathVariable",httpHeaders));
        log.info("Search for Product ...");
        Optional<Product> optionalProduct = searchProductUseCase.findById(id);
        if(optionalProduct.isPresent()){
            log.warn("Product {} found ...", id);
            log.info("Mapping Response ...");
            ProductResponseDto productResponseDto = productWebMapper.mapToDto(optionalProduct.get());
            log.info("Response was mapped successfully ... {}", productResponseDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        }
        log.warn("Product {} not found ...", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
