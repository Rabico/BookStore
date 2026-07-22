package book.store.repository.book;

import book.store.dto.BookSearchParametersDto;
import book.store.model.Book;
import book.store.repository.SpecificationBuilder;
import book.store.repository.SpecificationProviderManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> authorSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = (Root<Book> root, CriteriaQuery<?> query,
                                    CriteriaBuilder cb) -> null;
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(authorSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(authorSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(authorSpecificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbns()));
        }
        if (searchParameters.descriptions() != null && searchParameters.descriptions().length > 0) {
            spec = spec.and(authorSpecificationProviderManager
                    .getSpecificationProvider("description")
                    .getSpecification(searchParameters.descriptions()));
        }
        if (searchParameters.coverImages() != null && searchParameters.coverImages().length > 0) {
            spec = spec.and(authorSpecificationProviderManager
                    .getSpecificationProvider("coverImage")
                    .getSpecification(searchParameters.coverImages()));
        }
        return spec;
    }
}
