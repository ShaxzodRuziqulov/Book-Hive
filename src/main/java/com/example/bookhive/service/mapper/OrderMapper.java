package com.example.bookhive.service.mapper;

import com.example.bookhive.entity.Book;
import com.example.bookhive.entity.Order;
import com.example.bookhive.service.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDto, Order> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "books", target = "bookIds")
    OrderDto toDto(Order order);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "bookIds", target = "books")
    Order toEntity(OrderDto orderDto);

    default List<Long> mapBooksToIds(List<Book> books) {
        return books.stream().map(Book::getId).collect(Collectors.toList());
    }

    default List<Book> mapIdsToBooks(List<Long> ids) {
        if (ids == null) return null;
        return ids.stream().map(id -> {
            Book book = new Book();
            book.setId(id);
            return book;
        }).collect(Collectors.toList());
    }
}



