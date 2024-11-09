package org.sale.project.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.sale.project.entity.ProductVariant;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    void updateProduct(@MappingTarget ProductVariant productVariant, ProductVariant productVariantUpdate);


}
