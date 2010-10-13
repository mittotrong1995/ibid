package se.chalmers.ibid.model.biddingservices;

import java.util.List;

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.bid.Bid;
import se.chalmers.ibid.model.biddingservices.util.BidState;
import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.exception.InstanceNotFoundException;
import se.chalmers.ibid.model.product.Product;

public interface BiddingServices {
	
	public Account retrieveAccount(Long accountId) throws InstanceNotFoundException;
	
	public Product retrieveProduct(Long productId) throws InstanceNotFoundException;
	
	public Account addMoney(Long accountId, double amount) throws InstanceNotFoundException;
	
	public int getNumberProducts(List<String> keywords, Long categoryId) throws InstanceNotFoundException;

	public List<Product> searchProducts(List<String> keywords, Long categoryId, int startIndex, int count) throws InstanceNotFoundException;
	
	public Bid bid(Long accountId, Long productId, double amount) throws InstanceNotFoundException;
	
	public int getNumberBids(Long accountId) throws InstanceNotFoundException;
	
	public List<Bid> getBids(Long account, int startIndex, int count) throws InstanceNotFoundException;
	
	public int getNumberOngoingBids(Long accountId) throws InstanceNotFoundException;

	public List<Bid> getOngoingBids(Long accountId, int startIndex, int count) throws InstanceNotFoundException;
	
	public BidState getBidState(Bid bid);
	
	public List<Category> retrieveCategories();
	
}
