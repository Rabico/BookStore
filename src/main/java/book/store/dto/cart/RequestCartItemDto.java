package book.store.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestCartItemDto(
        @NotNull(message = "Book ID cannot be blank")
        Long bookId,
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
}
