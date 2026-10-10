package book.store.mapper;

import book.store.config.MapperConfig;
import book.store.dto.book.BookDto;
import book.store.dto.book.BookDtoWithoutCategoryIds;
import book.store.dto.book.CreateBookRequestDto;
import book.store.model.Book;
import book.store.model.Category;
import java.util.HashSet;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto toDto, Book book) {
        Set<Category> categories = book.getCategories();
        Set<Long> categoryIds = new HashSet<>();
        for (Category category : categories) {
            categoryIds.add(category.getId());
        }
        toDto.setCategoryIds(categoryIds);
    }
}
