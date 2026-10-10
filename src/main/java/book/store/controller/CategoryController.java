package book.store.controller;

import book.store.dto.book.BookDtoWithoutCategoryIds;
import book.store.dto.category.CategoryDto;
import book.store.dto.category.CreateCategoryRequestDto;
import book.store.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Return a paginated list of all created categories")
    public Page<CategoryDto> getAll(@ParameterObject Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID",
            description = "Returns the category with the specified ID")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create category",
            description = "Creates a new category")
    public CategoryDto createBook(@RequestBody @Valid
                                  CreateCategoryRequestDto createCategoryRequestDto) {
        return categoryService.createCategory(createCategoryRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update category",
            description = "Update the category with the specified ID")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody
            @Valid CreateCategoryRequestDto createCategoryRequestDto) {
        return categoryService.updateCategoryById(id, createCategoryRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category",
            description = "Delete the category with the specified ID")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/books")
    @Operation(summary = "Get category by ID",
            description = "Returns the category with the specified ID")
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable Long id, @ParameterObject Pageable pageable) {
        return categoryService.getBooksByCategoryId(id, pageable);
    }

}
