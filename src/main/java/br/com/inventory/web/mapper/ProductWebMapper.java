package br.com.inventory.web.mapper;

import br.com.inventory.web.application.domain.Product;
import br.com.inventory.web.application.enums.ProductTypeEnum;
import br.com.inventory.web.dto.request.ProductRequestDto;
import br.com.inventory.web.dto.response.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductWebMapper {


    public Product mapToDomain(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setDescription(productRequestDto.getDescription());
        product.setAmount(productRequestDto.getAmount());
        product.setType(ProductTypeEnum.valueOf(productRequestDto.getType()));
        return product;
    }

    public ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto(product.getId());
    }
}
