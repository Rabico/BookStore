package book.store.repository.book;

import book.store.dto.BookSearchParametersDto;
import book.store.model.Book;
import book.store.repository.SpecificationBuilder;
import book.store.repository.SpecificationProviderManager;
import book.store.repository.book.spec.SpecificationName;
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
                                    CriteriaBuilder cb) -> cb.conjunction();
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationName.AUTHOR.getValue())
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationName.TITTLE.getValue())
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationName.ISBN.getValue())
                    .getSpecification(searchParameters.isbns()));
        }
        if (searchParameters.descriptions() != null && searchParameters.descriptions().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationName.DESCRIPTION.getValue())
                    .getSpecification(searchParameters.descriptions()));
        }
        return spec;
    }
}
