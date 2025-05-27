package com.purely.services;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.OrderRequestDto;
import com.purely.exceptions.CartNotFoundException;
import com.purely.exceptions.OrderNotFoundException;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity<ApiResponseDto<?>> createOrder(OrderRequestDto request) throws UserNotFoundException, ServiceLogicException, CartNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws UserNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, OrderNotFoundException;
}
