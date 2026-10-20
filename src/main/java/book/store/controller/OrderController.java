package book.store.controller;

import book.store.dto.order.CreateRequestOrderDto;
import book.store.dto.order.OrderDto;
import book.store.dto.order.OrderItemDto;
import book.store.dto.order.OrderStatusDto;
import book.store.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Create a new order",
               description = "Creates a new order for the authenticated user")
    public OrderDto createOrder(@RequestBody @Valid CreateRequestOrderDto
                                            createRequestOrderDto) {
        return orderService.createOrder(createRequestOrderDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all orders",
               description = "Retrieves all orders for the authenticated user")
    public Page<OrderDto> getAllOrders(@ParameterObject Pageable pageable) {
        return orderService.getAllOrders(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update an order status",
               description = "Updates an order status by Admin")
    public OrderDto updateOrderStatus(@RequestBody @Valid OrderStatusDto
                                            orderStatusDto, @PathVariable Long id) {
        return orderService.setOrderStatus(id, orderStatusDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/items")
    @Operation(summary = "Get order items",
               description = "Retrieves order items by order id")
    public List<OrderItemDto> getOrderItems(@PathVariable Long id) {
        return orderService.getOrderItems(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderid}/items/{itemid}")
    @Operation(summary = "Get order item",
               description = "Retrieves order item by order id and item id")
    public OrderItemDto getOrderItem(@PathVariable Long orderid, @PathVariable Long itemid) {
        return orderService.getOrderItem(orderid, itemid);
    }
}
