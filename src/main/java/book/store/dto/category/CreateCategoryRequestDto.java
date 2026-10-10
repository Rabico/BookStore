package book.store.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(

        @NotBlank(message = "Name must not be blank")
        String name,
        String description
) {
}
