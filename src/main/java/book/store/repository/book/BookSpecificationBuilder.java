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

    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = (Root<Book> root, CriteriaQuery<?> query,
                                    CriteriaBuilder cb) -> null;
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbns()));
        }
        if (searchParameters.descriptions() != null && searchParameters.descriptions().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("description")
                    .getSpecification(searchParameters.descriptions()));
        }
        return spec;
    }
}
