package book.store.repository.book.spec;

import book.store.model.Book;
import book.store.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CoverImageSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey() {
        return "coverImage";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get("coverImage").in(Arrays.stream(params).toArray());
    }
}
