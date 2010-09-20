package se.chalmers.ibid.model.category;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("categoriaDao")
public class CategoryDaoHibernate extends GenericDaoHibernate<Category, Long> implements CategoryDao{
	
	public Category buscarCategoriaPorNombre(String nombre) throws InstanceNotFoundException{
		String stringConsulta;
		stringConsulta = "SELECT c FROM Categoria c WHERE c.nombre LIKE :nombre";
		Query consulta = getSession().createQuery(stringConsulta);
		consulta.setParameter("nombre", nombre);
		Category categoria;
		try {
			categoria = (Category) consulta.uniqueResult();
			if (categoria == null) throw new InstanceNotFoundException(nombre, Category.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return categoria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> buscarCategorias(){
		String stringConsulta;
		stringConsulta = "SELECT c FROM Categoria c";
		Query consulta = getSession().createQuery(stringConsulta);
		return consulta.list();
	}
	
}
