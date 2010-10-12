package se.chalmers.ibid.model.userservices;

import se.chalmers.ibid.model.exception.DuplicateInstanceException;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;

public interface UserServices {
	
	public Long registerUser(String login, String cleanPassword, UserProfileDetails userInfo) throws DuplicateInstanceException;

	public User login(String login, String password, boolean encryptedPassword) throws InstanceNotFoundException, IncorrectPasswordException;

	public User searchUser(Long userId) throws InstanceNotFoundException;

	public void updateUser(Long userId, UserProfileDetails userInfo) throws InstanceNotFoundException;

	public void changePassword(Long userId, String oldCleanPassword, String newCleanPassword) throws IncorrectPasswordException,InstanceNotFoundException;

}
