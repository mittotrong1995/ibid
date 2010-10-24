package se.chalmers.ibid.model.bid;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import se.chalmers.ibid.model.dao.GenericDaoHibernate;

@Repository("bidDao")
public class BidDaoHibernate extends GenericDaoHibernate<Bid, Long> implements BidDao {
	
	private Query prepareQueryProduct(Long productId, boolean op){
		String stringQuery;
    	if(op) stringQuery = "SELECT b FROM Bid b WHERE b.product.productId = :productId ORDER BY b.bidId";
    	else stringQuery = "SELECT COUNT(b) FROM Bid b WHERE b.product.productId = :productId ORDER BY b.bidId";
    	Query consulta = getSession().createQuery(stringQuery).setParameter("productId", productId);
    	return consulta;
	}
	
	public int getNumberBidsByProduct(Long productId) {
	    try {
	    	Query query = prepareQueryProduct(productId, false);
    		long number = (Long) query.uniqueResult();
    		return (int) number;

    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Bid> searchBidsByProduct(Long productId, int startIndex, int count) {
	    try {
	    	Query query = prepareQueryProduct(productId, true);
            return query.setFirstResult(startIndex).
                    		setMaxResults(count).
                    		list();
	    } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}

	private Query prepareQueryAccount(Long accountId, boolean op){
		String stringQuery;
    	if(op) stringQuery = "SELECT b FROM Bid b WHERE b.account.accountId = :accountId ORDER BY b.bidId";
    	else stringQuery = "SELECT COUNT(b) FROM Bid b WHERE b.account.accountId = :accountId ORDER BY b.bidId";
    	Query query = getSession().createQuery(stringQuery).setParameter("accountId", accountId);
    	return query;
	}
	
	
	public int getNumberBidsByAccount(Long accountId) {
	    try {
	    	Query query = prepareQueryAccount(accountId, false);
    		long num = (Long) query.uniqueResult();
    		return (int) num;

    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Bid> searchBidsByAccount(Long accountId, int startIndex, int count) {
	    try {
	    	Query query = prepareQueryAccount(accountId, true);
            return query.setFirstResult(startIndex).
                    		setMaxResults(count).
                    		list();
	    } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	private Query prepareQueryOngoingBids(Long accountId, boolean type){
		String stringQuery;
    	if(type) stringQuery = "SELECT b FROM Bid b WHERE (b.account.accountId = :accountId AND b.product.ended=FALSE) ORDER BY b.bidId";
    	else stringQuery = "SELECT COUNT(b) FROM Bid b WHERE (b.account.accountId = :accountId AND b.product.ended=FALSE) ORDER BY b.bidId";
    	Query query = getSession().createQuery(stringQuery).setParameter("accountId", accountId);
    	return query;
	}
	
	public int getNumberOngoingBidsByAccount(Long accountId) {
	    try {
	    	Query query = prepareQueryOngoingBids(accountId, false);
    		long number = (Long) query.uniqueResult();
    		return (int) number;

    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Bid> searchOngoingBidsByAccount(Long accountId, int startIndex, int count) {
	    try {
	    	Query query = prepareQueryOngoingBids(accountId, true);
            return query.setFirstResult(startIndex).
                    		setMaxResults(count).
                    		list();
	    } catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
}
