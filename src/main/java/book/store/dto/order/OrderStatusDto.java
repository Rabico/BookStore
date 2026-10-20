package book.store.dto.order;

import book.store.model.Order;
import jakarta.validation.constraints.NotNull;

public record OrderStatusDto(

        @NotNull(message = "Status is required")
        Order.Status status
) {
}
