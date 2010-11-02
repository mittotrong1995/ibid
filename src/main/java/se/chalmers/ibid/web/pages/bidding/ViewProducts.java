package se.chalmers.ibid.web.pages.bidding;

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.ProductsGridDataSource;


@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class ViewProducts{

	private final static int PRODUCTS_PER_PAGE=10;
	private Long categoryId;
	private String keywords;
	private List<String> keywordsList;
	private List<Product> list;
	private ProductsGridDataSource productsGridDataSource;
	private Product product;
	//private NumberFormat format;
	
	@Inject
	private Locale locale;
	
	@Inject
	private BiddingServices biddingServices;

	public List<Product> getProductsList() {
		return list;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductsGridDataSource getProductsGridDataSource() {
		return productsGridDataSource;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}
	
	public Format getStringFormat() {
		return new MessageFormat("");
	}

	/*public NumberFormat getFormat() {
		return new NumberFormat();
	}

	public void setFormat(NumberFormat format) {
		this.format = format;
	}*/

	public int getRowsPerPage() {
		return PRODUCTS_PER_PAGE;
	}
	
	void onActivate(Long categoryId, String keywords) {
		this.categoryId = categoryId;
		this.keywords = keywords;
		if(keywords!=null) keywordsList = Arrays.asList(keywords.split("\\s+"));
		else keywordsList=new ArrayList<String>();
		
		productsGridDataSource = new ProductsGridDataSource(biddingServices, keywordsList, categoryId);
	}
	
	Object [] onPassivate() {
		return new Object[] {categoryId, keywords};
	}
}
