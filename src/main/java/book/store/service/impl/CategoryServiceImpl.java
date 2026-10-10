package book.store.service.impl;

import book.store.dto.book.BookDtoWithoutCategoryIds;
import book.store.dto.category.CategoryDto;
import book.store.dto.category.CreateCategoryRequestDto;
import book.store.exception.EntityNotFoundException;
import book.store.mapper.BookMapper;
import book.store.mapper.CategoryMapper;
import book.store.model.Category;
import book.store.repository.book.BookRepository;
import book.store.repository.category.CategoryRepository;
import book.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find Category by id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = categoryMapper.toModel(createCategoryRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategoryById(Long id,
                                          CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find Category by id " + id)
        );
        category.setName(createCategoryRequestDto.name());
        category.setDescription(createCategoryRequestDto.description());

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find Category by id " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        return bookRepository.findAllByCategories_Id(id, pageable)
                .map(bookMapper::toDtoWithoutCategoryIds);
    }
}
