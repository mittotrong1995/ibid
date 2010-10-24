package se.chalmers.ibid.web.pages.users;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.userservices.UserProfileDetails;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.web.pages.Index;
import se.chalmers.ibid.web.services.AuthenticationPolicy;
import se.chalmers.ibid.web.services.AuthenticationPolicyType;
import se.chalmers.ibid.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_AUTHENTICATED)
public class UpdateProfile {

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

    void onPrepareForRender() throws InstanceNotFoundException {

        User user;

        user = userServices.searchUser(userSession
                .getUserProfileId());
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();

    }

    Object onSuccess() throws InstanceNotFoundException {

        userServices.updateUser(userSession.getUserProfileId(), new UserProfileDetails(name, surname, email));
        userSession.setFirstName(name);
        return Index.class;

    }

}