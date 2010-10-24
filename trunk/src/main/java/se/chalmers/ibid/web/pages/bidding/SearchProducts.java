package se.chalmers.ibid.web.pages.bidding;

import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class SearchProducts {
	
	@Property
	private String category;

	@Property
	private String string;
	
	@InjectPage
	private ViewProducts viewProducts;
	
	@Inject
	private BiddingServices biddingServices;
	
	public String getCategories() {
		List<Category> listCategories = biddingServices.retrieveCategories();
		Iterator<Category> iterator = listCategories.iterator();
		String categories = new String();
		Category cat;
		while(iterator.hasNext()) {
			cat = iterator.next();
			categories = categories + cat.getCategoryId() + "=" + cat.getName();
			if(iterator.hasNext()) categories = categories + ",";
		}
		return categories;
	}
	
	Object onSuccess() {
		if(category!=null) viewProducts.setCategoryId(new Long(category));
		else viewProducts.setCategoryId(null);
		viewProducts.setKeywords(string);
		return viewProducts;
	}

}
