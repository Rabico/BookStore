package book.store.service;

import book.store.dto.book.BookDtoWithoutCategoryIds;
import book.store.dto.category.CategoryDto;
import book.store.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryDto> getAll(Pageable pageable);

    CategoryDto getCategoryById(Long id);

    CategoryDto createCategory(CreateCategoryRequestDto createCategoryRequestDto);

    CategoryDto updateCategoryById(Long id, CreateCategoryRequestDto createCategoryRequestDto);

    void deleteCategoryById(Long id);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}

