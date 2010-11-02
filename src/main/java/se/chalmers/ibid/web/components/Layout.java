package se.chalmers.ibid.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import se.chalmers.ibid.web.pages.Index;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.CookiesManager;
import se.chalmers.ibid.web.util.UserSession;

public class Layout {
    @SuppressWarnings("unused")
    @Property
    @Parameter(required = false, defaultPrefix = "message")
    private String menuExplanation;

    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = "message")
    private String pageTitle;

    @Property
    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private Cookies cookies;

    @AuthenticationPolicy(AuthenticationPolicyType.ALL_AUTHENTICATED)
   	Object onActionFromLogout() {
        userSession = null;
        CookiesManager.removeCookies(cookies);
        return Index.class;
	}
    /*  
	public boolean isAdmin(){
		//return true;
		return userSession.getLogin().equals("admin");
	}
	*/
}