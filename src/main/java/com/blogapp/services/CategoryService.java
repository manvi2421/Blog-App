package com.blogapp.services;

import com.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //  Create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //  Update
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //  Delete
    public void deleteCategory(Integer categoryId);

    //  Get
    public CategoryDto getCategory(Integer categoryId);

    //  Get All
    public List<CategoryDto> getAllCategory();
}
