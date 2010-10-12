package se.chalmers.ibid.model.userservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.exception.DuplicateInstanceException;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.user.UserDao;
import se.chalmers.ibid.model.userservices.util.PasswordEncrypter;

@Service("UserServices")
@Transactional
public class UserServicesImpl implements UserServices {
	
    @Autowired
	private UserDao userDao;
    

	public Long registerUser(String login, String cleanPassword, UserProfileDetails userInfo) 
		throws DuplicateInstanceException {
		
		try {
			userDao.searchUserByLogin(login);
			throw new DuplicateInstanceException(login,	User.class.getName());
		}catch (InstanceNotFoundException e){
			String encryptedPassword = PasswordEncrypter.crypt(cleanPassword);
			
			Account account = new Account(0);
			
			User user = new User(login, encryptedPassword, userInfo.getFirstName(), 
					             userInfo.getLastName(), userInfo.getEmail(), account);

			userDao.save(user);
			return user.getUserId();
		}
	}
	
	
	@Transactional(readOnly = true)
	public User login(String login, String password, boolean encryptedPassword) 
		throws InstanceNotFoundException, IncorrectPasswordException {

		User user = userDao.searchUserByLogin(login);
		String storedPassword = user.getPassword();
		
		if (encryptedPassword) {
			if (!password.equals(storedPassword)) {
				throw new IncorrectPasswordException(login);
			}
		} else {
			if (!PasswordEncrypter.isClearPasswordCorrect(password, storedPassword)) {
				throw new IncorrectPasswordException(login);
			}
		}
		return user;
	}
	
	
	@Transactional(readOnly = true)
	public User searchUser(Long userId) throws InstanceNotFoundException {
		return userDao.find(userId);
	}
	
	
	public void updateUser(Long userId, UserProfileDetails userInfo) throws InstanceNotFoundException {

		User user = userDao.find(userId);
		user.setName(userInfo.getFirstName());
		user.setSurname(userInfo.getLastName());
		user.setEmail(userInfo.getEmail());
	}

	public void changePassword(Long userId, String oldClearPassword, String newCleanPassword) throws IncorrectPasswordException, InstanceNotFoundException {

		User user = userDao.find(userId);
		
		String storedPassword = user.getPassword();

		if (!PasswordEncrypter.isClearPasswordCorrect(oldClearPassword, storedPassword)) {
			throw new IncorrectPasswordException(user.getLogin());
		}

		user.setPassword(PasswordEncrypter.crypt(newCleanPassword));	
	}
}
