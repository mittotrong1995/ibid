package se.chalmers.ibid.model.product;

import java.util.List;

import se.chalmers.ibid.model.dao.GenericDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;

public interface ProductDao extends GenericDao<Product, Long>{
	
	public List<Product> searchProductsByName(String name) throws InstanceNotFoundException;
	public int getNumberProductsBySpec(List<String> keywords, Long categoryId);
	public List<Product> searchProductsBySpec(List<String> keywords, Long categoryId, int startIndex, int count);

}
