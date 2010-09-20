package se.chalmers.ibid.model.category;

import java.util.List;

import se.chalmers.ibid.model.dao.GenericDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public interface CategoryDao extends GenericDao<Category, Long>{
	
	public Category buscarCategoriaPorNombre(String nombre) throws InstanceNotFoundException;
	public List<Category> buscarCategorias();
	
}
