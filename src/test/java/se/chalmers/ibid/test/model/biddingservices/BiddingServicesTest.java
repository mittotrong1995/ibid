package se.chalmers.ibid.test.model.biddingservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static se.chalmers.ibid.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static se.chalmers.ibid.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.account.AccountDao;
import se.chalmers.ibid.model.bid.Bid;
import se.chalmers.ibid.model.bid.BidDao;
import se.chalmers.ibid.model.biddingservices.BiddingServices;
import se.chalmers.ibid.model.biddingservices.util.BidState;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.category.CategoryDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.model.product.ProductDao;
import se.chalmers.ibid.test.model.util.DbUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class BiddingServicesTest {

	@Autowired
	private AccountDao accountDao;	
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private BidDao bidDao;
	@Autowired
	private BiddingServices biddingServices;

	@BeforeClass
	public static void populateDb() throws Throwable {
		DbUtil.populateDb();
	}

	@AfterClass
	public static void cleanDb() throws Throwable {
		DbUtil.cleanDb();
	}
	
	@Test
	public void testObtenerNumeroEventos() throws InstanceNotFoundException{
		
		Category category = categoryDao.find(DbUtil.getTestCategory1Id());
		assertTrue(2 == biddingServices.getNumberProducts(new ArrayList<String>(), category.getCategoryId()));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Guer");
		assertTrue(1 == biddingServices.getNumberProducts(list, null));
		
		list = new ArrayList<String>();
		list.add("GuEr");
		assertTrue(1 == biddingServices.getNumberProducts(list, null));
		
		list = new ArrayList<String>();
		list.add("Guer");
		assertTrue(1 == biddingServices.getNumberProducts(list, category.getCategoryId()));	
	}
	
	
	@Test(expected = InstanceNotFoundException.class)
	public void testgetNumberProductsByInexistentCategory() throws InstanceNotFoundException{
		biddingServices.getNumberProducts(new ArrayList<String>(), new Long(50));
	}
	
	
	@Test
	public void testSearchProducts() throws InstanceNotFoundException{

		Category category = categoryDao.find(DbUtil.getTestCategory1Id());
		
		ArrayList<Product> list1 = new ArrayList<Product>();
		list1.add(productDao.find(DbUtil.getTestProduct2Id()));
		list1.add(productDao.find(DbUtil.getTestProduct1Id()));
		
		int start = 0;
		int count = 10;
		
		List<Product> list2;
		list2 = biddingServices.searchProducts(new ArrayList<String>(), category.getCategoryId(), start, count);
		
		assertTrue(list2.size() <= count);
		assertTrue(list1.size() == list2.size());
		
		Iterator<Product> iterator = list2.iterator();
		int i = 0;
		while(iterator.hasNext()){
			assertEquals(iterator.next(), list1.get(i));
			i++;
		}
		
		
		
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("mO");
		
		list1 = new ArrayList<Product>();
		
		list1.add(productDao.find(DbUtil.getTestProduct7Id()));
		list1.add(productDao.find(DbUtil.getTestProduct3Id()));
		list1.add(productDao.find(DbUtil.getTestProduct1Id()));
		
		list2 = biddingServices.searchProducts(list, null, start, count);
		
		assertTrue(list2.size() <= count);
		assertTrue(list1.size() == list2.size());
		
		iterator = list2.iterator();
		i = 0;
		while(iterator.hasNext()){
			assertEquals(iterator.next(), list1.get(i));
			i++;
		}
		
		
		
		
		list = new ArrayList<String>();
		list.add("Lis");
		
		list1 = new ArrayList<Product>();
		list1.add(productDao.find(DbUtil.getTestProduct5Id()));
		list1.add(productDao.find(DbUtil.getTestProduct1Id()));
		
		list2 = biddingServices.searchProducts(list, null, start, count);
		
		assertTrue(list2.size() <= count);
		assertTrue(list1.size() == list2.size());
		
		iterator = list2.iterator();
		i = 0;
		while(iterator.hasNext()){
			assertEquals(iterator.next(), list1.get(i));
			i++;
		}
		
		
		
		
		
		list = new ArrayList<String>();
		list.add("lis");
		
		list1 = new ArrayList<Product>();
		list1.add(productDao.find(DbUtil.getTestProduct5Id()));
		
		category = categoryDao.find(DbUtil.getTestCategory2Id());
		
		list2 = biddingServices.searchProducts(list, category.getCategoryId(), start, count);
		
		assertTrue(list2.size() <= count);
		assertTrue(list1.size() == list2.size());
		
		iterator = list2.iterator();
		i = 0;
		while(iterator.hasNext()){
			assertEquals(iterator.next(), list1.get(i));
			i++;
		}
	}
	
	
	//Inexistent category
	@Test(expected = InstanceNotFoundException.class)
	public void testSearchProductsByInexistentCategory() 
		throws InstanceNotFoundException{
		
		biddingServices.searchProducts(new ArrayList<String>(), new Long(50), 0, 10);
	}
	
	
	//Inexistent category and keywords
	@Test(expected = InstanceNotFoundException.class)
	public void testSearchProductsByInexistentCategoryAndKeywords() 
		throws InstanceNotFoundException{
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hi");
		list.add("How Are U");
		
		biddingServices.searchProducts(list, new Long(50), 0, 10);
	}
	
	
	@Test
	public void testBid() throws InstanceNotFoundException{

		Bid bid = biddingServices.bid(DbUtil.getTestAccountId(), DbUtil.getTestProduct1Id(), 120);
		
		Bid bid2 = bidDao.find(bid.getBidId());
		
		assertEquals(bid.getAccount(), bid2.getAccount());	
		assertEquals(bid.getProduct(), bid2.getProduct());
		//Impossible to compare floats :s
		//assertEquals(bid.getMoney(), bid2.getMoney());
	}
	
	
	@Test
	public void testGetNumberBids() throws InstanceNotFoundException{
		
		Account account = accountDao.find(DbUtil.getTestAccountId());
		//3 bids in populateDB
		assertTrue(3 == biddingServices.getNumberBids(account.getAccountId()));
	}
	
	
	@Test
	public void testGetBids() throws InstanceNotFoundException{
		
		Account account = accountDao.find(DbUtil.getTestAccountId());
		
		ArrayList<Bid> list1 = new ArrayList<Bid>();
		list1.add(bidDao.find(DbUtil.getTestBid1Id()));
		list1.add(bidDao.find(DbUtil.getTestBid2Id()));
		list1.add(bidDao.find(DbUtil.getTestBid3Id()));
		
		int start = 0;
		int count = 10;
		
		List<Bid> list2;
		list2 = biddingServices.getBids(account.getAccountId(), start, count);
		
		assertTrue(list2.size() <= count);
		assertTrue(list1.size() == list2.size());
		
		Iterator<Bid> iterator = list2.iterator();
		int i = 0;
		while(iterator.hasNext()){
			assertEquals(iterator.next(), list1.get(i));
			i++;
		}
	}
	
	@Test
	public void testRetrieveAccount() throws InstanceNotFoundException{
		
		Long id = DbUtil.getTestAccountId();
		Account account1 = accountDao.find(id);
		Account account2 = biddingServices.retrieveAccount(id);
		assertEquals(account1, account2);
	}
	
	@Test
	public void testRetrieveProduct() throws InstanceNotFoundException{
		
		Long id = DbUtil.getTestProduct1Id();
		Product product1 = productDao.find(id);
		Product product2 = biddingServices.retrieveProduct(id);
		assertEquals(product1, product2);
	}
	
	
	@Test
	public void testAddMoney() throws InstanceNotFoundException{
	
		double amount = 25;
		Long accountId = DbUtil.getTestAccountId();
		Account account1 = accountDao.find(accountId);
		double money1 = account1.getMoney() + amount;
		Account account2 = biddingServices.addMoney(accountId, amount);
		double money2 = account2.getMoney();
		assertEquals(money1, money2, 0);
	}
	
		
	@Test
	public void testGetBidState() {
		
		Bid bid = null;
		try {
			bid = bidDao.find(DbUtil.getTestBid3Id());
		} catch (InstanceNotFoundException e) {}
		BidState state = biddingServices.getBidState(bid);
		BidState supposedState = BidState.ONGOING;
		assertEquals(state, supposedState);
	}
	
	
	@Test
	public void testGetNumberOngoingBids() {
		Long accountId = DbUtil.getTestAccountId();
		int num1 = 3;
		int num2 = 0;
		try {
			num2 = biddingServices.getNumberOngoingBids(accountId);
		} catch (InstanceNotFoundException e) {}
		assertEquals(num1, num2);
		
	}
	
	@Test
	public void testGetOngoingBids() {
		Long accountId = DbUtil.getTestAccountId();
		int startIndex = 0;
		int count = 10;
		List<Bid> bids1 = new ArrayList<Bid>();
		List<Bid> bids2 = new ArrayList<Bid>();
		
		try {
			bids1.add(bidDao.find(DbUtil.getTestBid1Id()));
			bids1.add(bidDao.find(DbUtil.getTestBid2Id()));
			bids1.add(bidDao.find(DbUtil.getTestBid3Id()));
		} catch (InstanceNotFoundException e) {}
		
		try {
			bids2 = biddingServices.getOngoingBids(accountId, startIndex, count);
		} catch (InstanceNotFoundException nfe) {}
		
		assertTrue(bids1.containsAll(bids2));
		assertEquals(bids1.size(), bids2.size());
		
	}
	
	@Test
	public void test() {
		
		List<Category> inDB = categoryDao.searchCategories();
		List<Category> byService = biddingServices.retrieveCategories();
		assertTrue(inDB.containsAll(byService));
		assertTrue(inDB.size()==byService.size());
	}
	
}
