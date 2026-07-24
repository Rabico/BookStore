package book.store.controller;

import book.store.dto.BookDto;
import book.store.dto.BookSearchParametersDto;
import book.store.dto.CreateBookRequestDto;
import book.store.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books",
            description = "Return a paginated list of all available books")
    public Page<BookDto> getAll(@ParameterObject Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID",
            description = "Returns the book with the specified ID")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @Operation(summary = "Create book",
            description = "Creates a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        return bookService.createBook(createBookRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book",
            description = "Update the book with the specified ID")
    public BookDto updateBook(@PathVariable Long id, @RequestBody
            @Valid CreateBookRequestDto createBookRequestDto) {
        return bookService.updateBookById(id, createBookRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book",
            description = "Delete the book with the specified ID")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search books",
            description = "Returns books matching the specified search criteria.")
    public Page<BookDto> searchBooks(BookSearchParametersDto params,
                                     @ParameterObject Pageable pageable) {
        return bookService.search(params, pageable);
    }
}
