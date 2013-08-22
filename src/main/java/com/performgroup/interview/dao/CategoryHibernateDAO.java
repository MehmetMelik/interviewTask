/**
 * 
 */
package com.performgroup.interview.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.performgroup.interview.domain.Category;
import com.performgroup.interview.exception.CategoryAlreadyExistsException;

/**
 * @author Melik
 *
 */
public class CategoryHibernateDAO extends HibernateDaoSupport implements
		CategoryDAO {

	@SuppressWarnings("unchecked")
	public Collection<Category> findAll() {
		return getHibernateTemplate().loadAll(Category.class);
	}

	public Category findById(Long categoryId) {
		return (Category) getHibernateTemplate().load(Category.class, categoryId);	
	}

	public Long save(Category category) throws CategoryAlreadyExistsException {
		try {
			return (Long)getHibernateTemplate().save(category);
		}
		catch (DataIntegrityViolationException _ex) {
			throw new CategoryAlreadyExistsException();
		}

	}

	public void delete(Category category) {
		getHibernateTemplate().delete(category);
	}
	
	public Category findByCategory(String category) {
		String queryString = "select cat from Category cat where category =?";
		List<Category> catList = getHibernateTemplate().find(queryString, category); 
		if (catList.size() == 0)
			return null;
		else 
			return catList.get(0);
		
	}

}
