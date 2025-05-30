package com.purely.controllers;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.CartItemRequestDto;
import com.purely.exceptions.ProductNotFoundException;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserNotFoundException;
import com.purely.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purely/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> addItemToCart(@RequestBody CartItemRequestDto requestDto)
            throws UserNotFoundException, ServiceLogicException, ProductNotFoundException {
        return cartService.addItemToCart(requestDto);
    }

    @GetMapping("/get/byUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(@RequestParam String id)
            throws UserNotFoundException, ServiceLogicException{
        return cartService.getCartItemsByUser(id);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(@RequestParam String userId, @RequestParam String productId)
            throws ServiceLogicException{
        return cartService.removeCartItemFromCart(userId, productId);
    }
}

