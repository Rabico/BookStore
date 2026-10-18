package book.store.repository.order;

import book.store.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT s FROM OrderItem s "
            + "LEFT JOIN FETCH s.book "
            + "WHERE s.order.id = ?1 AND s.id = ?2")
    Optional<OrderItem> findByIdAndOrderId(Long orderId, Long id);
}
