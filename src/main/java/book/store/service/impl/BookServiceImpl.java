package book.store.service.impl;

import book.store.dto.book.BookDto;
import book.store.dto.book.BookSearchParametersDto;
import book.store.dto.book.CreateBookRequestDto;
import book.store.exception.EntityNotFoundException;
import book.store.mapper.BookMapper;
import book.store.model.Book;
import book.store.model.Category;
import book.store.repository.book.BookRepository;
import book.store.repository.book.BookSpecificationBuilder;
import book.store.repository.category.CategoryRepository;
import book.store.service.BookService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto createBook(CreateBookRequestDto createBookRequestDto) {
        Book book = bookMapper.toModel(createBookRequestDto);
        validateCategoryId(createBookRequestDto.getCategoryIds(), book);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find Book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto
            createBookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );

        book.setTitle(createBookRequestDto.getTitle());
        book.setAuthor(createBookRequestDto.getAuthor());
        book.setIsbn(createBookRequestDto.getIsbn());
        book.setPrice(createBookRequestDto.getPrice());
        book.setDescription(createBookRequestDto.getDescription());
        book.setCoverImage(createBookRequestDto.getCoverImage());
        book.setCoverImage(createBookRequestDto.getCoverImage());
        validateCategoryId(createBookRequestDto.getCategoryIds(), book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find book by id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> search(BookSearchParametersDto params,
                                Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable)
                .map(bookMapper::toDto);
    }

    private void validateCategoryId(Set<Long> categoryId, Book book) {
        Set<Category> categories = new HashSet<>();
        for (Long id : categoryId) {
            categories.add(categoryRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Can't find category by id: " + id)
            ));
        }
        book.setCategories(categories);
    }
}
