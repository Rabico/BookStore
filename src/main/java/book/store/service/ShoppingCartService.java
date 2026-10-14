package book.store.service;

import book.store.dto.cart.RequestCartItemDto;
import book.store.dto.cart.ShoppingCartDto;
import book.store.dto.cart.UpdateCartItemDto;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addCartItemToShoppingCart(RequestCartItemDto cartItemDto);

    void updateCartItemInShoppingCart(
            UpdateCartItemDto updateCartItemDto, Long cartItemId);

    void deleteCartItemFromShoppingCart(Long cartItemId);
}
