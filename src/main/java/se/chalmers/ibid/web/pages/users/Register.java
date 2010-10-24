package se.chalmers.ibid.web.pages.users;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.exception.DuplicateInstanceException;
import se.chalmers.ibid.model.userservices.UserProfileDetails;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.pages.Index;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

    @Property
    private String login;

    @Property
    private String password;

    @Property
    private String retypePassword;

    @Property
    private String name;

    @Property
    private String surname;

    @Property
    private String email;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private UserServices userServices;

    @Component
    private Form registrationForm;

    @Component(id = "login")
    private TextField loginField;

    @Component(id = "password")
    private PasswordField passwordField;

    @Inject
    private Messages messages;

    private Long userId;

    void onValidateForm() {

        if (!registrationForm.isValid()) {
            return;
        }

        if (!password.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages
                    .get("error-passwordsDontMatch"));
        } else {

            try {
                userId = userServices.registerUser(login, password,
                		new UserProfileDetails(name, surname, email));
            } catch (DuplicateInstanceException e) {
                registrationForm.recordError(loginField, messages
                        .get("error-loginNameAlreadyExists"));
            }

        }

    }

    Object onSuccess() {

    	userSession = new UserSession();
        userSession.setUserProfileId(userId);
        userSession.setFirstName(name);
        userSession.setLogin(login);
        return Index.class;

    }

}