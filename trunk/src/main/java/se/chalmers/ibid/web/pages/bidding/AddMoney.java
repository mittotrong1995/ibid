package se.chalmers.ibid.web.pages.bidding;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class AddMoney{
	
    @SessionState(create=false)
    private UserSession userSession;

	@Property
	private String amount;
	
	private double amountDouble;
	
	@Component
	private Form addMoneyForm;

	@Component(id = "amount")
	private TextField amountTextField;

	@Inject
	private Locale locale;
	
	@Inject
	private Messages messages;
	
	@Inject
	private UserServices userServices;

	@Inject
	private BiddingServices biddingServices;

	void onValidateForm() {

		if (!addMoneyForm.isValid()) {
			return;
		}

		NumberFormat numberFormatter = NumberFormat.getInstance(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = numberFormatter.parse(amount, position);
		
		if (position.getIndex() != amount.length()){
			addMoneyForm.recordError(amountTextField, messages.format(
					"error-incorrectNumberFormat", amount));
			return;
		}else{
			amountDouble = number.doubleValue();
		}
		
		try {
			
			User user = userServices.searchUser(userSession.getUserProfileId());
			Account account = user.getAccount();
			biddingServices.addMoney(account.getAccountId(), amountDouble);
			
		} catch (InstanceNotFoundException e) {
			addMoneyForm.recordError(amountTextField, messages.format(
					"error-accountNotFound"));
		}
	}

	Object onSuccess(){
		return SuccessfulAddition.class;
	}

}
