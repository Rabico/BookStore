package book.store.repository;

import book.store.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book createBook(Book book);

    List<Book> getAll();

    Optional<Book> getBookById(Long id);
}
