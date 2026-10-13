package book.store.repository.book;

import book.store.model.Book;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b "
            + "LEFT JOIN FETCH b.categories "
            + "WHERE b.id = :id")
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b "
            + "LEFT JOIN FETCH b.categories")
    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByCategories_Id(Long categoryId, Pageable pageable);
}
