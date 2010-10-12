package se.chalmers.ibid.test.model.userservices;

import static org.junit.Assert.assertEquals;
import static se.chalmers.ibid.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static se.chalmers.ibid.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import se.chalmers.ibid.model.exception.DuplicateInstanceException;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.user.UserDao;
import se.chalmers.ibid.model.userservices.IncorrectPasswordException;
import se.chalmers.ibid.model.userservices.UserProfileDetails;
import se.chalmers.ibid.model.userservices.UserServices;
import se.chalmers.ibid.test.model.util.DbUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class UserServicesTest {

	@Autowired
	private UserDao userDao;	
	@Autowired
	private UserServices userServices;

	@BeforeClass
	public static void populateDb() throws Throwable {
		DbUtil.populateDb();
	}

	@AfterClass
	public static void cleanDb() throws Throwable {
		DbUtil.cleanDb();
	}
	

	@Test
	public void testRegisterUser() throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException{

		String login = "messi";
		String password = "tricampeones";
		String name = "Leo";
		String surname = "Messi";
		String email = "leomessi@gmail.com";

		UserProfileDetails userInfo = new UserProfileDetails(name, surname, email);
		Long userId = userServices.registerUser(login, password, userInfo);
		
		User user = userServices.login(login, password, false);
		assertEquals(userId, user.getUserId());
		assertEquals(login, user.getLogin());
		assertEquals(surname, user.getSurname());
		assertEquals(email, user.getEmail());	
	}
	
	
	@Test
	public void testLogin() throws IncorrectPasswordException, InstanceNotFoundException {

		User user = userServices.login(DbUtil.getTestLogin(), DbUtil.getTestCleanPassword(), false);
		User user2 = userServices.searchUser(DbUtil.getTestUserId());
		assertEquals(user, user2);
	}


	@Test
	public void testSearchUser() throws InstanceNotFoundException {

		User user = userServices.searchUser(DbUtil.getTestUserId());
		User user2 = userDao.find(DbUtil.getTestUserId());
		assertEquals(user, user2);
	}
	
	
	@Test
	public void testUpdateUser() throws InstanceNotFoundException, IncorrectPasswordException {
		
		String login = DbUtil.getTestLogin();
		String password = DbUtil.getTestCleanPassword();
		String name = "Albert";
		String surname = "Einstein";
		String email = "tito@einstein.com";
		
		UserProfileDetails infoUsuario = new UserProfileDetails(name, surname, email);
		userServices.updateUser(DbUtil.getTestUserId(), infoUsuario);
		
		userServices.login(login, password, false);
		User user = userServices.searchUser(DbUtil.getTestUserId());
		
		assertEquals(login, user.getLogin());
		assertEquals(name, user.getName());
		assertEquals(surname, user.getSurname());
		assertEquals(email, user.getEmail());
	}
	

	
	@Test
	public void testChangePassword() throws InstanceNotFoundException, IncorrectPasswordException {

		String newPassword = "newPassword";
		userServices.changePassword(DbUtil.getTestUserId(), DbUtil.getTestCleanPassword(), newPassword);
		userServices.login(DbUtil.getTestLogin(), newPassword, false);
	}
}
