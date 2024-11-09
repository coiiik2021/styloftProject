package org.sale.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.sale.project.entity.User;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    void updateUser(@MappingTarget User user, User userUpdate);
}
