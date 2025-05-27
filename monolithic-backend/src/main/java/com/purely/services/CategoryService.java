package com.purely.services;


import com.purely.dtos.responses.ApiResponseDto;
import com.purely.exceptions.CategoryAlreadyExistsException;
import com.purely.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCategoryById(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> createCategory(String name) throws ServiceLogicException, CategoryAlreadyExistsException;

}
