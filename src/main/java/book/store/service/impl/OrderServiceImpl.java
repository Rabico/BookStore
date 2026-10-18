package book.store.service.impl;

import book.store.dto.order.CreateRequestOrderDto;
import book.store.dto.order.OrderDto;
import book.store.dto.order.OrderItemDto;
import book.store.dto.order.OrderStatusDto;
import book.store.exception.EntityNotFoundException;
import book.store.mapper.OrderItemMapper;
import book.store.mapper.OrderMapper;
import book.store.model.CartItem;
import book.store.model.Order;
import book.store.model.OrderItem;
import book.store.model.ShoppingCart;
import book.store.model.User;
import book.store.repository.cart.ShoppingCartRepository;
import book.store.repository.order.OrderItemRepository;
import book.store.repository.order.OrderRepository;
import book.store.repository.user.UserRepository;
import book.store.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto createOrder(CreateRequestOrderDto createRequestOrderDto) {

        Order order = new Order();
        User user = getUser();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(createRequestOrderDto.shippingAddress());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException("Shopping cart not found, cannot create order"));
        Set<OrderItem> orderItems = new HashSet<>();
        shoppingCart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItems.add(orderItem);

        });
        order.setOrderItems(orderItems);
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            totalPrice = totalPrice.add(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        order.setTotal(totalPrice);
        Order savedOrder = orderRepository.save(order);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderDto> getAllOrders(Pageable pageable) {
        User user = getUser();
        return orderRepository.findByUser(user, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderDto setOrderStatus(Long orderId, OrderStatusDto orderStatusDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found")
        );
        order.setStatus(orderStatusDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Order not found")
        );

        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long orderItemId) {

        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(orderId, orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        return orderItemMapper
                .toDto(orderItem);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }
}
