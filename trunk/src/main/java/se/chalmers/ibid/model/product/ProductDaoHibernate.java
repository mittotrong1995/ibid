package se.chalmers.ibid.model.product;

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

@Repository("ProductDao")
public class ProductDaoHibernate extends GenericDaoHibernate<Product, Long> implements ProductDao{
	
	@SuppressWarnings("unchecked")
	public List<Product> searchProductsByName(String name) throws InstanceNotFoundException{
		String stringQuery;
		stringQuery = "SELECT p FROM Product p WHERE p.name LIKE :name";
		Query query = getSession().createQuery(stringQuery);
		query.setParameter("name", name);
		List<Product> products;
		try {
			products = (List<Product>) query.list();
			if (products.isEmpty()) throw new InstanceNotFoundException(name, Product.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return products;
	}
	
	private Query prepareSearch(List<String> keywords, Long categoryId, boolean op){
    	
		int numKeywords = keywords.size();
		String stringQuery;
    	if(op) stringQuery = "SELECT p FROM Product p WHERE p.date >= :currentDate";
    	else stringQuery = "SELECT COUNT(p) FROM Product p WHERE p.date >= :currentDate";
    	
    	switch(numKeywords){
    		case 0: break;
    		default: 
    				for (int i=0; i<numKeywords; i++) 
    						stringQuery = stringQuery + " AND UPPER(p.name) LIKE :word" + i;
    				break;
    	}
    	
    	if (categoryId!=null) stringQuery = stringQuery  + " AND p.category.categoryId = :categoryId";
    	stringQuery = stringQuery + " ORDER BY p.date asc";
    	
		Query query = getSession().createQuery(stringQuery);
		
		query.setCalendar("currentDate", Calendar.getInstance());
		
		for (int i=0; i<numKeywords; i++){	
			query = query.setParameter("word" + i, "%" + keywords.get(i).toUpperCase() + "%");
		}
		
		if (categoryId!=null) query = query.setParameter("categoryId", categoryId);
		
		return query;
	}
	
	public int getNumberProductsBySpec(List<String> keywords, Long categoryId){		
	    try {
	    	Query query;
	    	query = prepareSearch(keywords, categoryId, false);
	    	long num = (Long) query.uniqueResult();
    		return (int) num;
    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> searchProductsBySpec(List<String> keywords, Long categoryId, int startIndex, int count) {
	    try {
	    	Query query;
	    	query = prepareSearch(keywords, categoryId, true);
    		return query.setFirstResult(startIndex).
    						setMaxResults(count).
    						list();

    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}	
	
}
