package se.chalmers.ibid.model.biddingservices;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.account.AccountDao;
import se.chalmers.ibid.model.bid.Bid;
import se.chalmers.ibid.model.bid.BidDao;
import se.chalmers.ibid.model.biddingservices.util.BidState;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.category.CategoryDao;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;
import se.chalmers.ibid.model.product.ProductDao;

@Service("BiddingServices")
@Transactional
public class BiddingServicesImpl implements BiddingServices{
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private BidDao bidDao;

	public Product uploadProduct(String name, double startPrice, String categoryName) throws InstanceNotFoundException{
		
		Category category = categoryDao.searchCategoryByName(categoryName);
		Product product = new Product(name, startPrice, null, category);
		productDao.save(product);
		return product;
	}
	
	public Bid bid(Long accountId, Long productId, double amount) throws InstanceNotFoundException {
		
		Account account = accountDao.find(accountId);
		Product product = productDao.find(productId);
		Bid bid = new Bid(account, product, amount);
		account.setAvailableMoney(account.getAvailableMoney() - amount);
		account.setBlockedMoney(account.getBlockedMoney() + amount);
		bidDao.save(bid);
		product.setBestBid(bid);
		productDao.save(product);
		return bid;
	}
	
	@Transactional(readOnly = true)
	public int getNumberProducts(List<String> keywords, Long categoryId) throws InstanceNotFoundException {
		if (categoryId !=null)
			if (!categoryDao.exists(categoryId)) {
				throw new InstanceNotFoundException(categoryId, Category.class.getName());
			}
		return productDao.getNumberProductsBySpec(keywords, categoryId); 
	}

	@Transactional(readOnly = true)
	public List<Product> searchProducts(List<String> keywords, Long categoryId, int startIndex, int count) throws InstanceNotFoundException {
		if (categoryId !=null)
			if (!categoryDao.exists(categoryId)) {
				throw new InstanceNotFoundException(categoryId, Category.class.getName());
			}
		return productDao.searchProductsBySpec(keywords, categoryId, startIndex, count);
	}
	
	
	@Transactional(readOnly = true)
	public int getNumberBids(Long accountId) throws InstanceNotFoundException {

		if (!accountDao.exists(accountId)) {
			throw new InstanceNotFoundException(accountId, Account.class.getName());
		}
		return bidDao.getNumberBidsByAccount(accountId);
	}

	@Transactional(readOnly = true)
	public List<Bid> getBids(Long accountId, int startIndex, int count) throws InstanceNotFoundException {

		if (!accountDao.exists(accountId)) {
			throw new InstanceNotFoundException(accountId, Account.class.getName());
		}
		return bidDao.searchBidsByAccount(accountId, startIndex, count);
	}
	
	@Transactional(readOnly = true)
	public int getNumberBidsByProduct(Long productId) throws InstanceNotFoundException {

		if (!productDao.exists(productId)) {
			throw new InstanceNotFoundException(productId, Product.class.getName());
		}
		return bidDao.getNumberBidsByProduct(productId);
	}

	@Transactional(readOnly = true)
	public List<Bid> getBidsByProduct(Long productId) throws InstanceNotFoundException {

		if (!productDao.exists(productId)) {
			throw new InstanceNotFoundException(productId, Product.class.getName());
		}
		int number = getNumberBidsByProduct(productId);
		return bidDao.searchBidsByProduct(productId, 0, number);
	}
	
	@Transactional(readOnly = true)
	public int getNumberOngoingBids(Long accountId) throws InstanceNotFoundException {

		if (!accountDao.exists(accountId)) {
			throw new InstanceNotFoundException(accountId, Account.class.getName());
		}
		return bidDao.getNumberOngoingBidsByAccount(accountId);
	}

	@Transactional(readOnly = true)
	public List<Bid> getOngoingBids(Long accountId, int startIndex, int count) throws InstanceNotFoundException {

		if (!accountDao.exists(accountId)) {
			throw new InstanceNotFoundException(accountId, Account.class.getName());
		}
		return bidDao.searchOngoingBidsByAccount(accountId, startIndex, count);
	}
	
	@Transactional(readOnly = true)
	public Account retrieveAccount(Long accountId) throws InstanceNotFoundException {
		
		Account account = accountDao.find(accountId);
		return account;
	}
	
	@Transactional(readOnly = true)
	public Product retrieveProduct(Long productId) throws InstanceNotFoundException {
		
		Product product = productDao.find(productId);
		return product;
	}
	
	public Account addMoney(Long accountId, double amount) throws InstanceNotFoundException {
		
		Account account = retrieveAccount(accountId);
		account.setAvailableMoney(account.getAvailableMoney() + amount);
		return account;
	}
	
	public Account giveMoneyBack(Long accountId, double amount) throws InstanceNotFoundException {
		
		Account account = retrieveAccount(accountId);
		account.setAvailableMoney(account.getAvailableMoney() + amount);
		account.setBlockedMoney(account.getBlockedMoney() - amount);
		return account;
	}

	public BidState getBidState(Bid bid) {
		BidState state = BidState.ONGOING;
		
		Product product = bid.getProduct();
		
		if(Calendar.getInstance().before(product.getDate())) state = BidState.ONGOING;
		else{
			Bid winnerBid = product.getBestBid(); 
			
			if(winnerBid.getBidId() == bid.getBidId()) state = BidState.WON;
			else state = BidState.LOST;
		}
		
		return state;
	}
	
	public List<Category> retrieveCategories() {
		return categoryDao.searchCategories();
	}
}
