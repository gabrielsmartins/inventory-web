package br.com.inventory.web.controller;


import br.com.inventory.application.domain.Product;
import br.com.inventory.application.ports.in.CreateProductUseCase;
import br.com.inventory.application.ports.in.SearchProductUseCase;
import br.com.inventory.common.annotations.WebAdapter;
import br.com.inventory.web.dto.request.ProductRequestDto;
import br.com.inventory.web.dto.response.ProductResponseDto;
import br.com.inventory.web.mapper.ProductWebMapper;
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


@WebAdapter
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final ProductWebMapper productWebMapper;

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
