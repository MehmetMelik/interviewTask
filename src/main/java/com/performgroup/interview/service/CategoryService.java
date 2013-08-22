package com.performgroup.interview.service;

import java.util.Collection;

import com.performgroup.interview.domain.Category;
import com.performgroup.interview.exception.CategoryAlreadyExistsException;

public interface CategoryService {
	
	Category getCategory(Long categoryId);

	Collection<Category> listCategories();

	Long addCategory(Category category) throws CategoryAlreadyExistsException;

	void deleteCategory(Category category);
	
	Category findCategory(String category); 

}
