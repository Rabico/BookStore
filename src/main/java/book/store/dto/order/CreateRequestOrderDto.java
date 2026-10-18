package book.store.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CreateRequestOrderDto(
        @NotBlank(message = "Shipping address is required")
        String shippingAddress
) {
}
