package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //  Create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto , HttpStatus.CREATED);
    }

    //  Update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
        categoryDto = categoryService.updateCategory(categoryDto , categoryId);
        return new ResponseEntity<>(categoryDto , HttpStatus.OK);
    }

    //  Delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity(new ApiResponse("Category Deleted Successfully!!" , "true") , HttpStatus.OK);
    }

    //  Get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        return new ResponseEntity<>(categoryService.getCategory(categoryId) , HttpStatus.OK);
    }

    //  Get All
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory() , HttpStatus.OK);
    }
}
