package book.store.repository;

import book.store.dto.BookDto;
import book.store.exception.EntityNotFoundException;
import book.store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    default BookDto updateBook(Long id, BookDto bookDto) {
        Book book = findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + id + " not found"));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setDescription(bookDto.getDescription());
        book.setCoverImage(bookDto.getCoverImage());
        save(book);
        return bookDto;
    }
}
