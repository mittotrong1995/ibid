package se.chalmers.ibid.web.pages.bidding;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.SessionState;
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
public class ViewAccount{

	private Long accountId;
	private Account account;
	
	@Inject
	private BiddingServices biddingServices;
	
	@Inject
	private UserServices userServices;
		
	@Inject
	private Locale locale;
	
    @SessionState(create=false)
    private UserSession userSession;
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Account getAccount() {
		return account;
	}

	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}
	
	void onActivate() {
		try{
			User user = userServices.searchUser(userSession.getUserProfileId());
			this.accountId = user.getAccount().getAccountId();
			
			account = biddingServices.retrieveAccount(accountId);
		
		} catch (InstanceNotFoundException e){}
	}
	
	void onPassivate() {}

}