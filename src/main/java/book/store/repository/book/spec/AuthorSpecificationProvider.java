package book.store.repository.book.spec;

import book.store.model.Book;
import book.store.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {

    private static final String AUTHOR_KEY = "author";

    @Override
    public String getKey() {
        return AUTHOR_KEY;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("author")),
                "%" + params[0].toLowerCase() + "%");
    }
}
