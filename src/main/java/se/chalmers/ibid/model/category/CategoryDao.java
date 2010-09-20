package se.chalmers.ibid.model.category;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface CategoryDao extends GenericDao<Category, Long>{
	
	public Category buscarCategoriaPorNombre(String nombre) throws InstanceNotFoundException;
	public List<Category> buscarCategorias();
	
}
