package com.springboot.blogapp.service;

import java.util.List;
import com.springboot.blogapp.dto.CategoryDto;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);

	CategoryDto getCategory(Long categoryId);

	List<CategoryDto> getAllCategories();

	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

	void deleteCategory(Long categoryId);
}