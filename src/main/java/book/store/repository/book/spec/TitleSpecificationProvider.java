package book.store.repository.book.spec;

import book.store.model.Book;
import book.store.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return SpecificationName.TITTLE.getValue();
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(SpecificationName.TITTLE.getValue())),
                "%" + params[0].toLowerCase() + "%");
    }
}
