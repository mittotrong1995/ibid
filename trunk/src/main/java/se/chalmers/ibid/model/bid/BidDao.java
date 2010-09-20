package se.chalmers.ibid.model.bid;

import java.util.List;

import se.chalmers.ibid.model.dao.GenericDao;

public interface BidDao extends GenericDao<Bid, Long> {
	
	public int getNumberBidsByProduct(Long productId);
	public List<Bid> searchBidsByProduct(Long productId, int startIndex, int count);
	public int getNumberBidsByAccount(Long accountId);
	public List<Bid> searchBidsByAccount(Long accountId, int startIndex, int count);
}
