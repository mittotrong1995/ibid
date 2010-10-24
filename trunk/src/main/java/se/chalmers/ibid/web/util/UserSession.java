package se.chalmers.ibid.web.util;


public class UserSession {

	private Long userProfileId;
	private String firstName;
	private String login;

	public Long getUserProfileId() {
		return userProfileId;
	}
	
	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
