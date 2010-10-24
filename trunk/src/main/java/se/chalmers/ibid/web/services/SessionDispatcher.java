package se.chalmers.ibid.web.services;

import java.io.IOException;

import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.userservices.IncorrectPasswordException;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.util.CookiesManager;
import se.chalmers.ibid.web.util.UserSession;

public class SessionDispatcher implements Dispatcher {

	private ApplicationStateManager applicationStateManager;
	private Cookies cookies;
	private UserServices userServices;

	public SessionDispatcher(ApplicationStateManager applicationStateManager,
			Cookies cookies, UserServices userServices) {
		
		this.applicationStateManager = applicationStateManager;
		this.cookies = cookies;
		this.userServices = userServices;
		
	}

	public boolean dispatch(Request request, Response response)
			throws IOException {

		if (!applicationStateManager.exists(UserSession.class)) {

			String loginName = CookiesManager.getLoginName(cookies);
			if (loginName == null) {
				return false;
			}

			String encryptedPassword = CookiesManager
					.getEncryptedPassword(cookies);
			if (encryptedPassword == null) {
				return false;
			}

			try {

				User user = userServices.login(loginName, encryptedPassword, true);
				UserSession userSession = new UserSession();
				userSession.setUserProfileId(user.getUserId());
				userSession.setFirstName(user.getName());
				applicationStateManager.set(UserSession.class, userSession);

			} catch (InstanceNotFoundException e) {
				CookiesManager.removeCookies(cookies);
			} catch (IncorrectPasswordException e) {
				CookiesManager.removeCookies(cookies);
			}

		}

		return false;

	}

}
