package br.com.inventory.web.controller;


import br.com.inventory.application.domain.Product;
import br.com.inventory.application.ports.in.CreateProductUseCase;
import br.com.inventory.common.annotations.WebAdapter;
import br.com.inventory.web.dto.request.ProductRequestDto;
import br.com.inventory.web.dto.response.ProductResponseDto;
import br.com.inventory.web.mapper.ProductWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@WebAdapter
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductWebMapper productWebMapper;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody  @Valid ProductRequestDto productRequestDto, UriComponentsBuilder uriBuilder){
        Product product = productWebMapper.mapToDomain(productRequestDto);
        Product createdProduct = createProductUseCase.create(product);
        ProductResponseDto productResponseDto = productWebMapper.mapToDto(createdProduct);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productResponseDto);
    }
}
