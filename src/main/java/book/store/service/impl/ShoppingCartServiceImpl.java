package book.store.service.impl;

import book.store.dto.cart.RequestCartItemDto;
import book.store.dto.cart.ShoppingCartDto;
import book.store.dto.cart.UpdateCartItemDto;
import book.store.exception.EntityNotFoundException;
import book.store.mapper.ShoppingCartMapper;
import book.store.model.CartItem;
import book.store.model.ShoppingCart;
import book.store.model.User;
import book.store.repository.book.BookRepository;
import book.store.repository.cart.CartItemRepository;
import book.store.repository.cart.ShoppingCartRepository;
import book.store.repository.user.UserRepository;
import book.store.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;

    @Override
    public ShoppingCartDto getShoppingCart() {

        return shoppingCartMapper.toDto(createOrGetShoppingCart());
    }

    @Override
    public ShoppingCartDto addCartItemToShoppingCart(RequestCartItemDto cartItemDto) {

        ShoppingCart shoppingCart = createOrGetShoppingCart();
        Optional<CartItem> checkedCartItem = cartItemRepository.findByBookIdAndShoppingCartId(
                cartItemDto.bookId(), shoppingCart.getId());
        if (checkedCartItem.isPresent()) {
            Integer newQuantity = checkedCartItem.get().getQuantity() + cartItemDto.quantity();
            updateCartItemInShoppingCart(new UpdateCartItemDto(
                    newQuantity), checkedCartItem.get().getId()
            );
            return shoppingCartMapper.toDto(createOrGetShoppingCart());
        }
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(bookRepository.findById(cartItemDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Book with id " + cartItemDto.bookId() + " not found")
        ));
        cartItem.setQuantity(cartItemDto.quantity());
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toDto(createOrGetShoppingCart());
    }

    @Override
    public void updateCartItemInShoppingCart(
            UpdateCartItemDto updateCartItemDto, Long cartItemId) {
        ShoppingCart shoppingCart = createOrGetShoppingCart();
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                shoppingCart.getId()).orElseThrow(
                    () -> new EntityNotFoundException("Cart item with id "
                            + cartItemId + " not found")
        );
        cartItem.setQuantity(updateCartItemDto.quantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItemFromShoppingCart(Long cartItemId) {
        ShoppingCart shoppingCart = createOrGetShoppingCart();
        if (cartItemRepository.findByIdAndShoppingCartId(cartItemId,
                shoppingCart.getId()).isEmpty()) {
            throw new EntityNotFoundException("Cart item with id " + cartItemId + " not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    private ShoppingCart createOrGetShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return shoppingCartRepository.findByUser(user).orElseGet(
                () -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return shoppingCartRepository.save(newCart);
                }
        );
    }
}
