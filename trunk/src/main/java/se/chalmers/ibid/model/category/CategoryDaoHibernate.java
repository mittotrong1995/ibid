package se.chalmers.ibid.model.category;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

@Repository("categoryDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category, Long> implements CategoryDao{
	
	public Category searchCategoryByName(String name) throws InstanceNotFoundException{
		String stringQuery;
		stringQuery = "SELECT c FROM Category c WHERE c.name LIKE :name";
		Query query = getSession().createQuery(stringQuery);
		query.setParameter("name", name);
		
		Category category;
		try {
			category = (Category) query.uniqueResult();
			if (category == null) throw new InstanceNotFoundException(name, Category.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return category;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> searchCategories(){
		String stringConsulta;
		stringConsulta = "SELECT c FROM Category c";
		Query consulta = getSession().createQuery(stringConsulta);
		return consulta.list();
	}
	
}
