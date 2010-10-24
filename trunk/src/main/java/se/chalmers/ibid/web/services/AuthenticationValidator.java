package se.chalmers.ibid.web.services;

import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.MetaDataLocator;

import se.chalmers.ibid.web.util.UserSession;

public class AuthenticationValidator {

	private final static String LOGIN_PAGE = "users/Login";

	private final static String INIT_PAGE = "Index";
	
	private final static String DENIED_ACCESS = "users/Denied";
	

	public static final String PAGE_AUTHENTICATION_TYPE = "ibid.page-authentication-type";
	public static final String EVENT_HANDLER_AUTHENTICATION_TYPE = "ibid.event-handler-authentication-type";

	public static String checkForPage(String pageName,
			ApplicationStateManager applicationStateManager,
			ComponentSource componentSource, MetaDataLocator locator) {

		String redirectPage = null;
		Component page = componentSource.getPage(pageName);
		try {
			String policyAsString = locator.findMeta(PAGE_AUTHENTICATION_TYPE,
					page.getComponentResources(), String.class);

			AuthenticationPolicyType policy = AuthenticationPolicyType
					.valueOf(policyAsString);
			redirectPage = check(policy, applicationStateManager);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}
		return redirectPage;
	}

	public static String checkForComponentEvent(String pageName,
			String componentId, String eventId, String eventType,
			ApplicationStateManager applicationStateManager,
			ComponentSource componentSource, MetaDataLocator locator) {
		
		String redirectPage = null;
		String authenticationPolicyMeta = EVENT_HANDLER_AUTHENTICATION_TYPE
				+ "-" + eventId + "-" + eventType;
		authenticationPolicyMeta = authenticationPolicyMeta.toLowerCase();

		Component component = null;
		if (componentId == null) {
			component = componentSource.getPage(pageName);
		} else {
			component = componentSource.getComponent(pageName + ":"
					+ componentId);
		}
		try {
			String policyAsString = locator.findMeta(authenticationPolicyMeta,
					component.getComponentResources(), String.class);
			AuthenticationPolicyType policy = AuthenticationPolicyType
					.valueOf(policyAsString);
			redirectPage = AuthenticationValidator.check(policy,
					applicationStateManager);
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}
		return redirectPage;

	}

	public static String check(AuthenticationPolicy policy,
			ApplicationStateManager applicationStateManager) {

		if (policy != null) {
			return check(policy.value(), applicationStateManager);
		} else {
			return null;
		}

	}

	public static String check(AuthenticationPolicyType policyType,
			ApplicationStateManager applicationStateManager) {
		String redirectPage = null;

		boolean userAuthenticated = applicationStateManager.exists(UserSession.class);
		
		boolean esAdministrador = false;
		
		if(userAuthenticated) esAdministrador = applicationStateManager.get(UserSession.class).getLogin().equals("admin");

		switch (policyType) {
		
		case ALL_AUTHENTICATED:

			if (!userAuthenticated) {
				redirectPage = LOGIN_PAGE;
			}
			
			break;

		case AUTHENTICATED_USERS:

			if (!userAuthenticated) {
				redirectPage = LOGIN_PAGE;
			}else{
				//Si es admin, no puede acceder a páginas de usuario
				if (esAdministrador) {
					redirectPage = DENIED_ACCESS;
				}
			}
			
			break;
			
		case AUTHENTICATED_ADMIN:

			if (!userAuthenticated) {
				redirectPage = LOGIN_PAGE;
			}else{
				//Si es usuario normal, no puede acceder a páginas de administración
				if (!esAdministrador) {
					redirectPage = DENIED_ACCESS;
				}
			}
			
			break;

		case NON_AUTHENTICATED_USERS:

			if (userAuthenticated) {
				redirectPage = INIT_PAGE;
			}
			break;

		default:
			break;

		}

		return redirectPage;
	}

}
