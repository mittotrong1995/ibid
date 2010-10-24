package se.chalmers.ibid.web.util;

import java.util.List;

import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;

import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;

public class ProductsGridDataSource implements GridDataSource{

		private BiddingServices biddingServices;
		private List<Product> products;
		private List<String> keywordList;
		private Long categoryId;
		private int totalProducts;
		private int startIndex;
		private boolean categoryNotFound;

		public ProductsGridDataSource(BiddingServices biddingServices,
				List<String> keywords, Long categoryId) {

			this.biddingServices = biddingServices;
			this.keywordList = keywords;
			this.categoryId = categoryId;
			
			try {
				totalProducts = biddingServices.getNumberProducts(keywordList, categoryId);
			} catch (InstanceNotFoundException e) {
				categoryNotFound = true;
			}

		}

	    public int getAvailableRows() {
	        return totalProducts;
	    }

	    public Class<Product> getRowType() {
	        return Product.class;
	    }

	    public Object getRowValue(int index) {
	        return products.get(index-this.startIndex);
	    }

	    public void prepare(int startIndex, int endIndex,
	    	List<SortConstraint> sortConstraints) {

	        try {

	        	products = biddingServices.searchProducts(keywordList, categoryId, startIndex, endIndex-startIndex+1);
		        this.startIndex = startIndex;

			} catch (InstanceNotFoundException e) {
			}

	    }

	    public boolean getCategoryNotFound() {
	    	return categoryNotFound;
	    }

}
