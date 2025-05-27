package com.purely.services;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.ProductRequestDto;
import com.purely.exceptions.ProductNotFoundException;
import com.purely.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface ProductService {
    ResponseEntity<ApiResponseDto<?>> addProduct(ProductRequestDto requestDto) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getProductById(String productId) throws ServiceLogicException, ProductNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getProductByCategory(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> searchProducts(String searchKey) throws ServiceLogicException;

}
