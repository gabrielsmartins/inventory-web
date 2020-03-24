package br.com.inventory.web.controller;


import br.com.inventory.application.domain.Product;
import br.com.inventory.application.ports.in.CreateProductUseCase;
import br.com.inventory.application.ports.in.SearchProductUseCase;
import br.com.inventory.common.annotations.WebAdapter;
import br.com.inventory.web.dto.request.ProductRequestDto;
import br.com.inventory.web.dto.response.ProductResponseDto;
import br.com.inventory.web.mapper.ProductWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@WebAdapter
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final ProductWebMapper productWebMapper;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody  @Valid ProductRequestDto productRequestDto, UriComponentsBuilder uriBuilder){
        Product product = productWebMapper.mapToDomain(productRequestDto);
        Product createdProduct = createProductUseCase.create(product);
        ProductResponseDto productResponseDto = productWebMapper.mapToDto(createdProduct);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Optional<Product> optionalProduct = searchProductUseCase.findById(id);
        if(optionalProduct.isPresent()){
            ProductResponseDto productResponseDto = productWebMapper.mapToDto(optionalProduct.get());
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
