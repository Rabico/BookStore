package book.store.repository.order;

import book.store.model.Order;
import book.store.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT s FROM Order s "
            + "LEFT JOIN FETCH s.orderItems oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE s.user = ?1")
    Page<Order> findByUser(User user, Pageable pageable);

    @Query("SELECT s FROM Order s "
            + "LEFT JOIN FETCH s.orderItems oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE s.id = ?1")
    Optional<Order> findById(Long id);

}
