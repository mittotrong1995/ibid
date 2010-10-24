package se.chalmers.ibid.web.pages.users;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.userservices.IncorrectPasswordException;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.pages.Index;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.CookiesManager;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_AUTHENTICATED)
public class ChangePassword {

    @Property
    private String oldPassword;

    @Property
    private String newPassword;

    @Property
    private String retypeNewPassword;

    @SessionState(create=false)
    private UserSession userSession;

    @Component
    private Form changePasswordForm;

    @Inject
    private Cookies cookies;

    @Inject
    private Messages messages;

    @Inject
    private UserServices userServices;

    void onValidateForm() throws InstanceNotFoundException {

        if (!changePasswordForm.isValid()) {
            return;
        }

        if (!newPassword.equals(retypeNewPassword)) {
            changePasswordForm
                    .recordError(messages.get("error-passwordsDontMatch"));
        } else {

            try {
                userServices.changePassword(userSession.getUserProfileId(), oldPassword, newPassword);
            } catch (IncorrectPasswordException e) {
                changePasswordForm.recordError(messages.get("error-invalidPassword"));
            }

        }

    }

    Object onSuccess() {

        CookiesManager.removeCookies(cookies);
        return Index.class;

    }

}
