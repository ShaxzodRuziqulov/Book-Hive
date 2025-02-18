package com.example.bookhive.web.rest;

import com.example.bookhive.entity.Book;
import com.example.bookhive.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookhive.service.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookResource {
    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BookDto bookDto) throws URISyntaxException {
        BookDto result = bookService.create(bookDto);
        return ResponseEntity.created(new URI("/api/book/create/" + result.getId())).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody BookDto bookDto, @PathVariable Long id) throws URISyntaxException {
        if (bookDto.getId() != null && !bookDto.getId().equals(id)) {
            return ResponseEntity.badRequest().body("Invalid id");
        }
        BookDto result = bookService.update(bookDto);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<BookDto> findAllBooks = bookService.findAllBooks();
        return ResponseEntity.ok(findAllBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Book result = bookService.findById(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().body("Book deleted successfully: " + id);
    }
}

