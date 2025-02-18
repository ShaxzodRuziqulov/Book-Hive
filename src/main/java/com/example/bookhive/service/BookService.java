package com.example.bookhive.service;

import com.example.bookhive.entity.Book;
import com.example.bookhive.repository.BookRepository;
import com.example.bookhive.service.dto.BookDto;
import com.example.bookhive.service.mapper.BookMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDto create(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    public BookDto update(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    public List<BookDto> findAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public Book findById(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found :" + id));
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        bookRepository.delete(book);
    }
    public BookDto updateStock(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));

        book.setQuantityInStock(book.getQuantityInStock() + quantity);
        bookRepository.save(book);

        return bookMapper.toDto(book);
    }
}
