package se.chalmers.ibid.web.pages.bidding;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.bid.Bid;
import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class ProductDetails {

	private final static int BIDS_PER_PAGE = 10;

	private Long productId;
	@Persist
	private Product product;
	private User user;
	private Long accountId;

	@Persist
	private int startIndex;

	@Inject
	private BiddingServices biddingServices;

	@Inject
	private UserServices userServices;

	@Inject
	private Messages messages;

	@Inject
	private Locale locale;

	/************************************* ZONA AJAX ***************************************/

	@SessionState(create = false)
	private UserSession userSession;

	@SuppressWarnings("unused")
	@Property
	private List<Bid> bids;

	@SuppressWarnings("unused")
	@Property
	private Bid bid;

	@SuppressWarnings("unused")
	@Property
	private List<Bid> bidsProduct;

	@SuppressWarnings("unused")
	@Property
	private Bid bidProduct;

	@InjectComponent
	private Zone bidsZone;

	@InjectComponent
	private Zone formZone;

	@Component
	private Form bidForm;

	@Property
	private String amount;

	private double amountDouble;

	private NumberFormat format;

	@Component(id = "amount")
	private TextField amountTextField;

	public String buildBidString(Bid bid) {

		String infoBid = bid.getProduct().getName() + " (" + bid.getMoney()
				+ ")\n";

		return infoBid;
	}

	Object onValidateForm() {

		if (!bidForm.isValid()) {
			return null;
		}

		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(amount, position);

		if (position.getIndex() != amount.length()) {
			bidForm.recordError(amountTextField, messages.format(
					"error-incorrectNumberFormat", amount));
			return new MultiZoneUpdate("bidsZone", bidsZone.getBody()).add(
					"formZone", formZone.getBody());
		} else {
			amountDouble = number.doubleValue();
		}

		try {

			if (((biddingServices.retrieveProduct(productId).getBestBid() != null) && 
				(amountDouble <= biddingServices.retrieveProduct(productId).getBestBid().getMoney()))
				|| (amountDouble <= biddingServices.retrieveProduct(productId).getStartprice())) {
				
					bidForm.recordError(amountTextField, messages.format("error-low-bid", amount));
					return new MultiZoneUpdate("bidsZone", bidsZone.getBody()).add("formZone", formZone.getBody());

			} else {
				if ((amountDouble > biddingServices.retrieveAccount(accountId).getMoney())) {
					bidForm.recordError(amountTextField, messages.format("error-not-enough-money", amount));
					return new MultiZoneUpdate("bidsZone", bidsZone.getBody()).add("formZone", formZone.getBody());
				}else{
					amountDouble = number.doubleValue();
					biddingServices.bid(accountId, productId, amountDouble);
				}
			}
		} catch (InstanceNotFoundException e) {
			bidForm.recordError(amountTextField, messages
					.format("error-accountNotFound"));
		}

		try {
			bids = biddingServices.getOngoingBids(accountId, startIndex,
					BIDS_PER_PAGE);
			return ProductDetails.class;
		} catch (InstanceNotFoundException e) {
		}
		return null;
	}

	Object onSuccess() {
		return new MultiZoneUpdate("bidsZone", bidsZone.getBody()).add(
				"formZone", formZone.getBody());
	}

	public boolean existsPreviousLink() {
		return (startIndex - BIDS_PER_PAGE >= 0);
	}

	public boolean existsNextLink() {
		int numberBids = 0;
		try {
			numberBids = biddingServices.getNumberOngoingBids(accountId);
		} catch (InstanceNotFoundException e) {
		}
		return (startIndex + BIDS_PER_PAGE < numberBids);
	}

	Object onActionFromPrevious() {
		startIndex = startIndex - BIDS_PER_PAGE;
		try {
			bids = biddingServices.getOngoingBids(accountId, startIndex,
					BIDS_PER_PAGE);
		} catch (InstanceNotFoundException e) {
		}
		return bidsZone.getBody();
	}

	Object onActionFromNext() {
		startIndex = startIndex + BIDS_PER_PAGE;
		try {
			bids = biddingServices.getOngoingBids(accountId, startIndex,
					BIDS_PER_PAGE);
		} catch (InstanceNotFoundException e) {
		}
		return bidsZone.getBody();
	}

	/************************************* ZONA AJAX ***************************************/

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public NumberFormat getFormat() {
		return format;
	}

	public void setFormat(NumberFormat format) {
		this.format = format;
	}

	public BiddingServices getBiddingServices() {
		return biddingServices;
	}

	public void setBiddingServices(BiddingServices biddingServices) {
		this.biddingServices = biddingServices;
	}

	public boolean existBids() {
		try {
			return (biddingServices.getNumberBidsByProduct(productId) != 0);
		} catch (InstanceNotFoundException e) {
		}
		return false;
	}

	public String getProductName() {
		return product.getName();
	}

	Object onActionFromClicker() {
		return formZone.getBody();
	}

	Object onActionFromClicker2() {
		return formZone.getBody();
	}

	void onActivate(Long productId) {
		try {
			this.productId = productId;
			product = biddingServices.retrieveProduct(productId);
			bidsProduct = biddingServices.getBidsByProduct(productId);

			user = userServices.searchUser(userSession.getUserProfileId());
			accountId = user.getAccount().getAccountId();
			startIndex = 0;
			bids = biddingServices.getOngoingBids(accountId, startIndex,
					BIDS_PER_PAGE);

		} catch (InstanceNotFoundException e) {
		}

		bidsZone.getBody();
	}

	Long onPassivate() {
		return productId;
	}
}