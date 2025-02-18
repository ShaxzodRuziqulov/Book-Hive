package com.example.bookhive.service.mapper;

import com.example.bookhive.entity.Book;
import com.example.bookhive.service.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDto, Book> {
    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);

}
