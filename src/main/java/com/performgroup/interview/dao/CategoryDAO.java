/**
 * 
 */
package com.performgroup.interview.dao;

import java.util.Collection;

import com.performgroup.interview.domain.Category;
import com.performgroup.interview.domain.Video;
import com.performgroup.interview.exception.CategoryAlreadyExistsException;

/**
 * @author Melik
 *
 */
public interface CategoryDAO {
	
	/**
	 * Find all categories in the system
	 * 
	 * @return a collection of <code>Category</code>
	 */
	Collection<Category> findAll();
	
	/**
	 * Returns a specific category
	 * 
	 * @param categoryId
	 * @return a <code>Category</code> identified by the categoryId
	 */
	Category findById(Long categoryId);
	
	/**
	 * Persists a <code>Category</code> object into the system
	 * 
	 * @param category the <code>Category</code> object to persist
	 * @throws CategoryAlreadyExistException 
	 */
	Long save(Category category) throws CategoryAlreadyExistsException;
	
	/**
	 * Deletes a <code>Category</code> object from the system
	 * 
	 * @param category the <code>Category</code> object to delete
	 */
	void delete(Category category);
	
	/**
	 * Returns a specific category
	 * 
	 * @param category
	 * @return a <code>Category</code> identified by the category
	 */
	Category findByCategory(String category); 

}
