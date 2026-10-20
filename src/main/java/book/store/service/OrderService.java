package book.store.service;

import book.store.dto.order.CreateRequestOrderDto;
import book.store.dto.order.OrderDto;
import book.store.dto.order.OrderItemDto;
import book.store.dto.order.OrderStatusDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto createOrder(CreateRequestOrderDto createRequestOrderDto);

    Page<OrderDto> getAllOrders(Pageable pageable);

    OrderDto setOrderStatus(Long orderId, OrderStatusDto orderStatusDto);

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long orderItemId);
}
