package com.performgroup.interview.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.performgroup.interview.dao.CategoryDAO;
import com.performgroup.interview.domain.Category;
import com.performgroup.interview.domain.Video;
import com.performgroup.interview.exception.CategoryAlreadyExistsException;
import com.performgroup.interview.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	
	private CategoryDAO categoryDAO;

	/**
	 * @return the categoryDAO
	 */
	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	/**
	 * @param categoryDAO the categoryDAO to set
	 */
	@Resource
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	public Category getCategory(Long categoryId) {
		return getCategoryDAO().findById(categoryId);
	}

	public Collection<Category> listCategories() {
		return getCategoryDAO().findAll();
	}

	public Long addCategory(Category category) throws CategoryAlreadyExistsException {
		return getCategoryDAO().save(category);
	}

	public void deleteCategory(Category category) {
		getCategoryDAO().delete(category);
	}
	
	public Category findCategory(String category) {
		return getCategoryDAO().findByCategory(category);
	}

}
