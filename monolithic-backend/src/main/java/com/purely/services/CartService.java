package com.purely.services;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.CartItemRequestDto;
import com.purely.exceptions.ProductNotFoundException;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<ApiResponseDto<?>> addItemToCart(CartItemRequestDto requestDto) throws UserNotFoundException, ServiceLogicException, ProductNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws UserNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException;

}
