package se.chalmers.ibid.model.product;

import java.util.List;

import se.chalmers.ibid.model.dao.GenericDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public interface ProductDao extends GenericDao<Product, Long>{
	
	public List<Product> buscarEventosPorNombre(String nombre) throws InstanceNotFoundException;
	public int getNumeroEventosPorEspecificaciones(List<String> palabrasClave, Long idCategoria, boolean esAdmin);
	public List<Product> buscarEventosPorEspecificaciones(List<String> palabrasClave, Long idCategoria, int startIndex, int count, boolean esAdmin);

}
