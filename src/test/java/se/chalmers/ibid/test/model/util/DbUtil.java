package se.chalmers.ibid.test.model.util;

import static se.chalmers.ibid.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static se.chalmers.ibid.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.account.AccountDao;
import se.chalmers.ibid.model.bid.Bid;
import se.chalmers.ibid.model.bid.BidDao;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.category.CategoryDao;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.model.product.ProductDao;
import se.chalmers.ibid.model.user.User;
import se.chalmers.ibid.model.user.UserDao;
import se.chalmers.ibid.model.userservices.util.PasswordEncrypter;
import se.chalmers.ibid.model.util.CalendarUtil;

public class DbUtil {

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE});

		transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");
		accountDao = (AccountDao) context.getBean("accountDao");
		userDao = (UserDao) context.getBean("userDao");
		categoryDao = (CategoryDao) context.getBean("categoryDao");
		productDao = (ProductDao) context.getBean("productDao");
		bidDao = (BidDao) context.getBean("bidDao");
	}
	
	private static Long testUserId;
	private static Long testAccountId;
	private static Long testCategory1Id;
	private static Long testCategory2Id;
	private static Long testCategory3Id;
	private static Long testProduct1Id;
	private static Long testProduct2Id;
	private static Long testProduct3Id;
	private static Long testProduct4Id;
	private static Long testProduct5Id;
	private static Long testProduct6Id;
	private static Long testProduct7Id;
	private static Long testProduct8Id;
	private static Long testProduct9Id;
	private static Long testBid1Id;
	private static Long testBid2Id;
	private static Long testBid3Id;
	
	private static AccountDao accountDao;
	private static UserDao userDao;
	private static CategoryDao categoryDao;
	private static ProductDao productDao;
	private static BidDao bidDao;
	
	private static String testEncryptedPassword;
	private static String testCleanPassword = "cleanPassword";
	private static String testLogin = "user1";
	
	private static PlatformTransactionManager transactionManager;

	public static Long getTestUserId() {
		return testUserId;
	}


	public static Long getTestAccountId() {
		return testAccountId;
	}


	public static Long getTestCategory1Id() {
		return testCategory1Id;
	}


	public static Long getTestCategory2Id() {
		return testCategory2Id;
	}


	public static Long getTestCategory3Id() {
		return testCategory3Id;
	}


	public static Long getTestProduct1Id() {
		return testProduct1Id;
	}


	public static Long getTestProduct2Id() {
		return testProduct2Id;
	}


	public static Long getTestProduct3Id() {
		return testProduct3Id;
	}


	public static Long getTestProduct4Id() {
		return testProduct4Id;
	}


	public static Long getTestProduct5Id() {
		return testProduct5Id;
	}


	public static Long getTestProduct6Id() {
		return testProduct6Id;
	}


	public static Long getTestProduct7Id() {
		return testProduct7Id;
	}


	public static Long getTestProduct8Id() {
		return testProduct8Id;
	}


	public static Long getTestProduct9Id() {
		return testProduct9Id;
	}
	
	public static Long getTestBid1Id() {
		return testBid1Id;
	}
	
	public static Long getTestBid2Id() {
		return testBid2Id;
	}

	public static Long getTestBid3Id() {
		return testBid3Id;
	}


	public static String getTestEncryptedPassword() {
		return testEncryptedPassword;
	}


	public static String getTestCleanPassword() {
		return testCleanPassword;
	}


	public static String getTestLogin() {
		return testLogin;
	}


	public static PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}


	public static void populateDb() throws Throwable {
		/*
		 * Since this method is supposed to be called from a @BeforeClass 
		 * method, it works directly with "TransactionManager", since 
		 * @BeforeClass methods with Spring TestContext do not run in the 
		 * context of a transaction (which is required for DAOs to work).
		 */

		TransactionStatus transactionStatus = transactionManager.getTransaction(null);
		
		Account account = new Account(0);
		User user = new User(testLogin, PasswordEncrypter.crypt(testCleanPassword), "joaquin", "sabina", "jsabina@gmail.com", account);
		Category category1 = new Category("Art");
		Category category2 = new Category("Books");
		Category category3 = new Category("Clothing");
		Product product1 = new Product("Mona Lisa", 100, CalendarUtil.getCalendar(25, 12, 2011, 19, 00), category1);
		Product product2 = new Product("Guernika", 500, CalendarUtil.getCalendar(23, 9, 2011, 14, 20), category1);
		Product product3 = new Product("Angels & Demons", 10, CalendarUtil.getCalendar(25, 7, 2011, 19, 00), category2);
		Product product4 = new Product("The pilars of the Earth", 12, CalendarUtil.getCalendar(25, 5, 2011, 9, 00), category2);
		Product product5 = new Product("Ulises", 15, CalendarUtil.getCalendar(22, 12, 2011, 19, 00), category2);
		Product product6 = new Product("Levi's jeans", 40, CalendarUtil.getCalendar(25, 12, 2011, 11, 00), category3);
		Product product7 = new Product("Massimo Dutti jacket", 80, CalendarUtil.getCalendar(15, 5, 2011, 9, 20), category3);
		Bid bid1 = new Bid(account, product1, 110);
		Bid bid2 = new Bid(account, product2, 610);
		Bid bid3 = new Bid(account, product3, 12);
		
		try {
			accountDao.save(account);
			
			userDao.save(user);
			testUserId = user.getUserId();
			testEncryptedPassword = user.getPassword();
			
			testAccountId = account.getAccountId();
			
			categoryDao.save(category1);
			categoryDao.save(category2);
			categoryDao.save(category3);
			testCategory1Id = category1.getCategoryId();
			testCategory2Id = category2.getCategoryId();
			testCategory3Id = category3.getCategoryId();
			
			productDao.save(product1);
			productDao.save(product2);
			productDao.save(product3);
			productDao.save(product4);
			productDao.save(product5);
			productDao.save(product6);
			productDao.save(product7);
			testProduct1Id = product1.getProductId();
			testProduct2Id = product2.getProductId();
			testProduct3Id = product3.getProductId();
			testProduct4Id = product4.getProductId();
			testProduct5Id = product5.getProductId();
			testProduct6Id = product6.getProductId();
			testProduct7Id = product7.getProductId();
			
			bidDao.save(bid1);
			bidDao.save(bid2);
			bidDao.save(bid3);
			testBid1Id = bid1.getBidId();
			testBid2Id = bid2.getBidId();
			testBid3Id = bid3.getBidId();
			
			transactionManager.commit(transactionStatus);
		} catch (Throwable e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}
	}
	

	public static void cleanDb() throws Throwable {

		TransactionStatus transactionStatus = transactionManager.getTransaction(null);
		
		try {
			
			bidDao.remove(testBid1Id);
			bidDao.remove(testBid2Id);
			bidDao.remove(testBid3Id);
			testBid1Id = null;
			testBid2Id = null;
			testBid3Id = null;
			
			productDao.remove(testProduct1Id);
			productDao.remove(testProduct2Id);
			productDao.remove(testProduct3Id);
			productDao.remove(testProduct4Id);
			productDao.remove(testProduct5Id);
			productDao.remove(testProduct6Id);
			productDao.remove(testProduct7Id);
			testProduct1Id = null;
			testProduct2Id = null;
			testProduct3Id = null;
			testProduct4Id = null;
			testProduct5Id = null;
			testProduct6Id = null;
			testProduct7Id = null;
			
			categoryDao.remove(testCategory1Id);
			categoryDao.remove(testCategory2Id);
			categoryDao.remove(testCategory3Id);
			testCategory1Id = null;
			testCategory2Id = null;
			testCategory3Id = null;
			
			userDao.remove(testUserId);
			testUserId = null;
			
			//accountDao.remove(testAccountId);
			testAccountId = null;

			transactionManager.commit(transactionStatus);
		} catch (Throwable e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}
	}

}
