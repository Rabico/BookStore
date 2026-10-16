package book.store.repository.cart;

import book.store.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndShoppingCartId(
            Long cartItemId, Long shoppingCartId);

    Optional<CartItem> findByBookIdAndShoppingCartId(Long bookId,
                                                     Long shoppingCartId);
}
