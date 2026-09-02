package book.store.service;

import book.store.dto.book.BookDto;
import book.store.dto.book.BookSearchParametersDto;
import book.store.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto createBook(CreateBookRequestDto createBookRequestDto);

    Page<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto createBookRequestDto);

    void deleteBookById(Long id);

    Page<BookDto> search(BookSearchParametersDto params, Pageable pageable);
}
