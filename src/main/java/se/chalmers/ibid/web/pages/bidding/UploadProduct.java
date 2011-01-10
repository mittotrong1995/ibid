package se.chalmers.ibid.web.pages.bidding;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.pages.Index;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UploadProduct {
	
	@Property
	private String category;
	
	@Property
	private String startprice;
	
	private double startpriceDouble;
	
	@Component(id = "startprice")
	private TextField amountTextField;
	
	@Component
	private Form uploadProductForm;

	@Property
	private String name;
	
	@Inject
	private Locale locale;
	
	@Inject
	private Messages messages;
	
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
	
	
	void onValidateForm() {

		if (!uploadProductForm.isValid()) {
			return;
		}

		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(startprice, position);
		
		if (position.getIndex() != startprice.length()){
			uploadProductForm.recordError(amountTextField, messages.format(
					"error-incorrectNumberFormat", startprice));
			return;
		}else{
			startpriceDouble = number.doubleValue();
		}
		
		try {
			biddingServices.uploadProduct(name, startpriceDouble, category);
		} catch (InstanceNotFoundException e) {
		}
	}
	
	
	Object onSuccess() {
		return Index.class;
	}

}
