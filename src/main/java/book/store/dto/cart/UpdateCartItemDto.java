package book.store.dto.cart;

import jakarta.validation.constraints.Min;

public record UpdateCartItemDto(
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) {
}
