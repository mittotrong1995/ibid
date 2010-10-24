package se.chalmers.ibid.web.pages.users;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.userservices.IncorrectPasswordException;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.pages.Index;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.CookiesManager;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Login {

    @Property
    private String login;

    @Property
    private String password;

    @Property
    private boolean rememberMyPassword;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private Cookies cookies;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;

    @Inject
    private UserServices userServices;

    private User user = null;


    void onValidateForm() {

        if (!loginForm.isValid()) {
            return;
        }

        try {
            user = userServices.login(login, password, false);
        } catch (InstanceNotFoundException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        } catch (IncorrectPasswordException e) {
            loginForm.recordError(messages.get("error-authenticationFailed"));
        }
    }

    Object onSuccess() {

    	userSession = new UserSession();
        userSession.setUserProfileId(user.getUserId());
        userSession.setFirstName(user.getName());
        userSession.setLogin(user.getLogin());
        

        if (rememberMyPassword) {
            CookiesManager.leaveCookies(cookies, login, user.getPassword());
        }
        return Index.class;

    }

	void onActivate() {}

}
