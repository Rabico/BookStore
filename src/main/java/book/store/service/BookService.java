package book.store.service;

import book.store.dto.BookDto;
import book.store.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {

    BookDto createBook(CreateBookRequestDto createBookRequestDto);

    List<BookDto> getAll();

    BookDto getBookById(Long id);

    BookDto updateBookById(Long id, BookDto bookDto);

    void deleteBookById(Long id);
}
