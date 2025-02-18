package com.example.bookhive.service.mapper;

import com.example.bookhive.entity.Customer;
import com.example.bookhive.service.dto.CustomerDto;
import com.example.bookhive.service.dto.RegisterUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "role.id", source = "roleId")
    Customer toEntity(CustomerDto customerDto);

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "name", source = "name")
    CustomerDto toDto(Customer customer);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "role.id", source = "roleId")
    Customer toUser(RegisterUserDto input);
}
