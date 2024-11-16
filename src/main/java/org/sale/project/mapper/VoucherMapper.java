package org.sale.project.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.sale.project.entity.User;
import org.sale.project.entity.Voucher;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateVoucher(@MappingTarget Voucher voucher, Voucher voucherUpdate);
}
