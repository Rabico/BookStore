package book.store.controller;

import book.store.dto.cart.RequestCartItemDto;
import book.store.dto.cart.ShoppingCartDto;
import book.store.dto.cart.UpdateCartItemDto;
import book.store.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingController {

    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get shopping cart",
            description = "Return a list of books in the shopping cart")
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add book to shopping cart",
            description = "Adds a book and quantity to the shopping cart")
    public ShoppingCartDto addCartItemToShoppingCart(@RequestBody @Valid
                                                         RequestCartItemDto cartItemDto) {
        return shoppingCartService.addCartItemToShoppingCart(cartItemDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update book quantity",
            description = "Update book quantity in Shopping Cart")
    public void updateCartItemInShoppingCart(@RequestBody @Valid
                                      UpdateCartItemDto updateCartItemDto, @PathVariable Long id) {
        shoppingCartService.updateCartItemInShoppingCart(updateCartItemDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item",
            description = "Delete cart item from Shopping Cart")
    public void deleteCartItemFromShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteCartItemFromShoppingCart(id);
    }
}
