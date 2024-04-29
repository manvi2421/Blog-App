package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
    private CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category , CategoryDto.class);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        category = categoryRepository.save(category);
        return categoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "category id" , categoryId));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        category = categoryRepository.save(category);

        return categoryToDto(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "category id" , categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "category id" , categoryId));

        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtoList;
    }
}
